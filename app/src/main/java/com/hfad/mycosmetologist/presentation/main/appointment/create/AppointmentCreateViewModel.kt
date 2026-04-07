package com.hfad.mycosmetologist.presentation.main.appointment.create

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.mycosmetologist.domain.useCase.session.ObserveAuthorizedWorkerId
import com.android.identity.util.UUID
import com.hfad.mycosmetologist.domain.entity.Appointment
import com.hfad.mycosmetologist.domain.useCase.appointment.CreateAppointment
import com.hfad.mycosmetologist.domain.useCase.appointment.GetAppointmentsByDate
import com.hfad.mycosmetologist.domain.useCase.client.GetClient
import com.hfad.mycosmetologist.domain.useCase.service.GetPriceList
import com.hfad.mycosmetologist.domain.useCase.worker.GetActualWorker
import com.hfad.mycosmetologist.domain.util.Result
import com.hfad.mycosmetologist.presentation.main.appointment.create.entity.CreateAppointmentAction
import com.hfad.mycosmetologist.presentation.main.appointment.create.entity.CreateAppointmentEvent
import com.hfad.mycosmetologist.presentation.main.appointment.create.entity.CreateAppointmentUiState
import com.hfad.mycosmetologist.presentation.main.appointment.create.entity.FormState
import com.hfad.mycosmetologist.presentation.navigation.AppScreen
import com.hfad.mycosmetologist.presentation.util.entity.PresentationAppointment
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

@HiltViewModel(assistedFactory = AppointmentCreateViewModel.Factory::class)
class AppointmentCreateViewModel @AssistedInject constructor(
    @Assisted private val appScreen: AppScreen.AppointmentCreate,
    private val getClient: GetClient,
    private val observeAuthorizedWorkerId: ObserveAuthorizedWorkerId,    private val getPriceList: GetPriceList,
    private val createAppointment: CreateAppointment,
    private val getAppointmentsByDate: GetAppointmentsByDate,
    private val clock: Clock
) : ViewModel() {

    private val clientId = appScreen.clientId

    private val _event = MutableSharedFlow<CreateAppointmentEvent>()
    val event = _event.asSharedFlow()

    private val zone = ZoneId.systemDefault()

    private val formState = MutableStateFlow(FormState())
    private val workerIdFlow = observeAuthorizedWorkerId()

    private val clientFlow = workerIdFlow.flatMapLatest { getClient(it, clientId) }

    private val priceListFlow = workerIdFlow.flatMapLatest { getPriceList(it) }
    private val appointmentsFlow =
        combine(workerIdFlow, formState.map { it.selectedDate }) { workerId, date ->
            workerId to date
        }.flatMapLatest { (workerId, date) ->
            getAppointmentsByDate(workerId, date.atStartOfDay(zone).toInstant())
        }

    val state: StateFlow<CreateAppointmentUiState> = combine(
        formState,
        clientFlow,
        priceListFlow,
        appointmentsFlow
    ) { form, clientResult, priceResult, appsResult ->
        val isLoading =
            clientResult !is Result.Success ||
                priceResult !is Result.Success ||
                appsResult !is Result.Success

        if (isLoading) {
            return@combine CreateAppointmentUiState(isLoading = true)
        }

        val client = clientResult.data
        val allPriceList = priceResult.data
        val activePriceList = allPriceList.filter { !it.isArchived }
        val appsList = appsResult.data

        val servicesMap = allPriceList.associateBy { it.id }

        CreateAppointmentUiState(
            workerId = client.workerId,
            selectedDate = form.selectedDate,
            startTime = form.startTime,
            endTime = form.endTime,
            selectedServices = form.selectedServices,
            about = form.about,

            clientName = client.name,
            clientNumber = "${client.phone.get(0)} (${
                client.phone.subSequence(
                    1,
                    4
                )
            }) ${client.phone.subSequence(4, 7)}-${
                client.phone.subSequence(
                    7,
                    9
                )
            }-${client.phone.subSequence(9, 11)}",

            appointmentList = appsList
                .filter { !it.cancelled }
                .map {
                    PresentationAppointment.toPresentationAppointment(
                        it,
                        servicesMap,
                        client.name
                    )
                },

            priceList = activePriceList,
            isLoading = false
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), CreateAppointmentUiState())

    fun onAction(action: CreateAppointmentAction) {
        when (action) {

            is CreateAppointmentAction.OnDateSelected ->
                formState.update {
                    it.copy(
                        selectedDate = LocalDate.ofInstant(
                            Instant.ofEpochMilli(
                                action.date ?: return
                            ), clock.zone
                        )
                    )
                }

            is CreateAppointmentAction.OnStartTimeSelected ->
                formState.update {
                    val updated = it.copy(startTime = LocalTime.of(action.hour, action.minutes))
                    updated.copy(endTime = calculateEndTime(updated))
                }

            is CreateAppointmentAction.OnEndTimeSelected ->
                formState.update { it.copy(endTime = LocalTime.of(action.hour, action.minutes)) }

            is CreateAppointmentAction.OnServicesSelected ->
                formState.update {
                    val updated = it.copy(selectedServices = formState.value.selectedServices.plus(action.service))
                    updated.copy(endTime = calculateEndTime(updated))
                }

            is CreateAppointmentAction.OnServicesDelete ->
                formState.update {
                    val updated = it.copy(selectedServices = formState.value.selectedServices.minus(action.service))
                    updated.copy(endTime = calculateEndTime(updated))
                }

            is CreateAppointmentAction.OnAboutChanged ->
                formState.update { it.copy(about = action.text) }

            CreateAppointmentAction.OnSaveClicked -> saveAppointment()
        }
    }

    private fun saveAppointment() {
        val ui = state.value

        if (ui.startTime == null || ui.endTime == null) {
            sendError("The time has not been chosen")
            return
        }

        if (ui.selectedServices.isEmpty()) {
            sendError("The services has not been chosen")
        }
        if (hasOverlap(ui)) {
            sendError("Время занято")
            return
        }

        viewModelScope.launch {
            createAppointment(
                Appointment(
                    id = UUID.randomUUID().toString(),
                    workerId = ui.workerId,
                    clientId = appScreen.clientId,
                    startTime = LocalDateTime.of(ui.selectedDate, ui.startTime)
                        .atZone(zone)
                        .toInstant(),
                    endTime = LocalDateTime.of(ui.selectedDate, ui.endTime)
                        .atZone(zone)
                        .toInstant(),
                    servicesIds = ui.selectedServices.map { it.id },
                    description = ui.about,
                    cancelled = false
                )
            ).collectLatest { result ->

                when (result) {

                    is Result.Loading -> {
                    }

                    is Result.Success -> {
                        _event.emit(CreateAppointmentEvent.Navigate)
                    }

                    is Result.Error -> {
                        sendError(result.exception.message ?: "Ошибка сохранения")
                    }
                }
            }
        }
    }

    private fun hasOverlap(ui: CreateAppointmentUiState): Boolean {
        val start = ui.startTime ?: return true
        val end = ui.endTime ?: return true

        return ui.appointmentList.any {
            start < LocalTime.parse(it.endTime) && end > LocalTime.parse(it.startTime)
        }
    }

    private fun calculateEndTime(form: FormState): LocalTime? {
        val start = form.startTime ?: return null
        val totalMinutes = form.selectedServices.sumOf { it.durationMinutes }
        return start.plusMinutes(totalMinutes.toLong())
    }


    private fun sendError(message: String) {
        viewModelScope.launch {
            _event.emit(CreateAppointmentEvent.Error(message))
        }
        Log.d("APPOINTMENT_DEBUG", "workerId=${state.value.workerId}")
        Log.d("APPOINTMENT_DEBUG", "clientId=${appScreen.clientId}")
        Log.d("APPOINTMENT_DEBUG", "services=${state.value.selectedServices.map { it.id }}")
        Log.e("AppointmentCreateViewModel", message)
    }

    fun onDateClick() {
        viewModelScope.launch {
            _event.emit(CreateAppointmentEvent.OpenDatePicker)
        }
    }

    fun onStartTimeClick() {
        viewModelScope.launch {
            _event.emit(CreateAppointmentEvent.OpenStartTimePicker)
        }
    }

    fun onEndTimeClick() {
        viewModelScope.launch {
            _event.emit(CreateAppointmentEvent.OpenEndTimePicker)
        }
    }

    fun onServicesClick() {
        viewModelScope.launch {
            _event.emit(CreateAppointmentEvent.OpenServicesDialog)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(appScreen: AppScreen.AppointmentCreate): AppointmentCreateViewModel
    }
}
