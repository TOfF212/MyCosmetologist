package com.hfad.mycosmetologist.presentation.main.appointment.change

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.identity.util.UUID
import com.hfad.mycosmetologist.domain.entity.Appointment
import com.hfad.mycosmetologist.domain.useCase.appointment.GetAppointmentById
import com.hfad.mycosmetologist.domain.useCase.appointment.GetAppointmentsByDate
import com.hfad.mycosmetologist.domain.useCase.appointment.UpdateAppointment
import com.hfad.mycosmetologist.domain.useCase.client.GetClient
import com.hfad.mycosmetologist.domain.useCase.service.GetPriceList
import com.hfad.mycosmetologist.domain.useCase.session.ObserveAuthorizedWorkerId
import com.hfad.mycosmetologist.domain.util.Result
import com.hfad.mycosmetologist.presentation.main.appointment.change.entity.ChangeAppointmentAction
import com.hfad.mycosmetologist.presentation.main.appointment.change.entity.ChangeAppointmentEvent
import com.hfad.mycosmetologist.presentation.main.appointment.change.entity.ChangeAppointmentUiState
import com.hfad.mycosmetologist.presentation.main.appointment.change.entity.FormState
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
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

@HiltViewModel(assistedFactory = AppointmentChangeViewModel.Factory::class)
class AppointmentChangeViewModel @AssistedInject constructor(
    @Assisted private val appScreen: AppScreen.AppointmentChange,
    private val getClient: GetClient,
    private val observeAuthorizedWorkerId: ObserveAuthorizedWorkerId,
    private val getPriceList: GetPriceList,
    private val updateAppointment: UpdateAppointment,
    private val getAppointmentsByDate: GetAppointmentsByDate,
    private val clock: Clock,
    private val getAppointmentById: GetAppointmentById,
) : ViewModel() {

    private val appointmentId = appScreen.id

    private val _event = MutableSharedFlow<ChangeAppointmentEvent>()
    val event = _event.asSharedFlow()

    private val zone = ZoneId.systemDefault()

    private val formState = MutableStateFlow(FormState())
    private val workerIdFlow = observeAuthorizedWorkerId()

    private val appointmentFlow = workerIdFlow.flatMapLatest { getAppointmentById(it, appointmentId) }

    private val currentAppointmentFlow = appointmentFlow.filterIsInstance<Result.Success<Appointment>>()

    private val clientFlow =
        combine(currentAppointmentFlow, workerIdFlow) { appointmentResult, workerId ->
            workerId to appointmentResult.data.clientId
        }.flatMapLatest { (workerId, clientId) ->
            getClient(workerId, clientId)
        }

    private val priceListFlow = workerIdFlow.flatMapLatest { getPriceList(it) }
    private val appointmentsFlow =
        combine(workerIdFlow, formState.map { it.selectedDate }) { workerId, date ->
            workerId to date
        }.flatMapLatest { (workerId, date) ->
            getAppointmentsByDate(workerId, date.atStartOfDay(zone).toInstant())
        }

    val state: StateFlow<ChangeAppointmentUiState> = combine(
        formState,
        clientFlow,
        priceListFlow,
        appointmentsFlow,
        appointmentFlow,
    ) { form, clientResult, priceResult, appsResult, appointmentResult ->
        val isLoading =
            clientResult !is Result.Success ||
                priceResult !is Result.Success ||
                appsResult !is Result.Success ||
                appointmentResult !is Result.Success

        if (isLoading) {
            return@combine ChangeAppointmentUiState(isLoading = true)
        }

        val appointment = appointmentResult.data
        val client = clientResult.data
        val allPriceList = priceResult.data
        val activePriceList = allPriceList.filter { !it.isArchived }
        val appsList = appsResult.data

        val servicesMap = allPriceList.associateBy { it.id }

        ChangeAppointmentUiState(
            appointmentId = appointment.id,
            clientId = appointment.clientId,
            workerId = client.workerId,
            selectedDate = form.selectedDate,
            startTime = form.startTime,
            endTime = form.endTime,
            selectedServices = form.selectedServices,
            about = form.about,
            clientName = client.name,
            clientNumber = "${client.phone[0]} (${client.phone.subSequence(1, 4)}) ${client.phone.subSequence(4, 7)}-${client.phone.subSequence(7, 9)}-${client.phone.subSequence(9, 11)}",
            appointmentList = appsList
                .filter { !it.cancelled && it.id != appointmentId }
                .map {
                    PresentationAppointment.toPresentationAppointment(
                        it,
                        servicesMap,
                        client.name,
                    )
                },
            priceList = activePriceList,
            isLoading = false,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ChangeAppointmentUiState())

    init {
        var formInitialized = false

        viewModelScope.launch {
            combine(currentAppointmentFlow, priceListFlow) { appointmentResult, priceResult ->
                appointmentResult.data to priceResult
            }.collectLatest { (appointment, priceResult) ->
                if (priceResult !is Result.Success || formInitialized) {
                    return@collectLatest
                }

                formInitialized = true
                formState.update {
                    it.copy(
                        selectedDate = appointment.startTime.atZone(zone).toLocalDate(),
                        startTime = appointment.startTime.atZone(zone).toLocalTime(),
                        endTime = appointment.endTime.atZone(zone).toLocalTime(),
                        selectedServices =  appointment.servicesIds.mapNotNull{ id -> priceResult.data.find { it.id == id }},
                        about = appointment.description,
                    )
                }
            }
        }
    }

    fun onAction(action: ChangeAppointmentAction) {
        when (action) {
            is ChangeAppointmentAction.OnDateSelected ->
                formState.update {
                    it.copy(
                        selectedDate = LocalDate.ofInstant(
                            Instant.ofEpochMilli(action.date ?: return),
                            clock.zone,
                        ),
                    )
                }

            is ChangeAppointmentAction.OnStartTimeSelected ->
                formState.update {
                    val updated = it.copy(startTime = LocalTime.of(action.hour, action.minutes))
                    updated.copy(endTime = calculateEndTime(updated))
                }

            is ChangeAppointmentAction.OnEndTimeSelected ->
                formState.update { it.copy(endTime = LocalTime.of(action.hour, action.minutes)) }

            is ChangeAppointmentAction.OnServicesSelected ->
                formState.update {
                    val updated = it.copy(
                        selectedServices = it.selectedServices.plus(action.service),
                    )
                    updated.copy(endTime = calculateEndTime(updated))
                }

            is ChangeAppointmentAction.OnServicesDelete ->
                formState.update {
                    val updatedServices = it.selectedServices.toMutableList()
                    val index = updatedServices.indexOfFirst { service ->
                        service.id == action.service.id
                    }
                    if (index >= 0) {
                        updatedServices.removeAt(index)
                    }

                    val updated = it.copy(selectedServices = updatedServices)
                    updated.copy(endTime = calculateEndTime(updated))
                }

            is ChangeAppointmentAction.OnAboutChanged ->
                formState.update { it.copy(about = action.text) }

            ChangeAppointmentAction.OnSaveClicked -> saveAppointment()
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
            return
        }

        if (hasOverlap(ui)) {
            sendError("Время занято")
            return
        }

        viewModelScope.launch {
            updateAppointment(
                Appointment(
                    id = ui.appointmentId.ifBlank { UUID.randomUUID().toString() },
                    workerId = ui.workerId,
                    clientId = ui.clientId,
                    startTime = LocalDateTime.of(ui.selectedDate, ui.startTime)
                        .atZone(zone)
                        .toInstant(),
                    endTime = LocalDateTime.of(ui.selectedDate, ui.endTime)
                        .atZone(zone)
                        .toInstant(),
                    servicesIds = ui.selectedServices.map { it.id },
                    description = ui.about,
                    cancelled = false,
                ),
            ).collectLatest { result ->
                when (result) {
                    is Result.Loading -> Unit
                    is Result.Success -> _event.emit(ChangeAppointmentEvent.Navigate)
                    is Result.Error -> sendError(result.exception.message ?: "Ошибка сохранения")
                }
            }
        }
    }

    private fun hasOverlap(ui: ChangeAppointmentUiState): Boolean {
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
            _event.emit(ChangeAppointmentEvent.Error(message))
        }
        Log.e("AppointmentChangeViewModel", message)
    }

    fun onDateClick() {
        viewModelScope.launch {
            _event.emit(ChangeAppointmentEvent.OpenDatePicker)
        }
    }

    fun onStartTimeClick() {
        viewModelScope.launch {
            _event.emit(ChangeAppointmentEvent.OpenStartTimePicker)
        }
    }

    fun onEndTimeClick() {
        viewModelScope.launch {
            _event.emit(ChangeAppointmentEvent.OpenEndTimePicker)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(appScreen: AppScreen.AppointmentChange): AppointmentChangeViewModel
    }
}
