package com.hfad.mycosmetologist.presentation.main.appointment.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.mycosmetologist.domain.entity.Appointment
import com.hfad.mycosmetologist.domain.useCase.appointment.GetAppointmentById
import com.hfad.mycosmetologist.domain.useCase.appointment.UpdateAppointment
import com.hfad.mycosmetologist.domain.useCase.client.GetClient
import com.hfad.mycosmetologist.domain.useCase.service.GetPriceList
import com.hfad.mycosmetologist.domain.useCase.session.ObserveAuthorizedWorkerId
import com.hfad.mycosmetologist.domain.useCase.worker.GetActualWorker
import com.hfad.mycosmetologist.domain.util.Result
import com.hfad.mycosmetologist.presentation.main.appointment.info.entity.AppointmentInfoUiState
import com.hfad.mycosmetologist.presentation.navigation.AppScreen
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@HiltViewModel(assistedFactory = AppointmentInfoViewModel.Factory::class)
class AppointmentInfoViewModel
@AssistedInject
constructor(
    @Assisted private val appScreen: AppScreen.AppointmentInfo,
    private val observeAuthorizedWorkerId: ObserveAuthorizedWorkerId, private val getAppointmentById: GetAppointmentById,
    private val getClient: GetClient,
    private val getPriceList: GetPriceList,
    private val updateAppointment: UpdateAppointment,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AppointmentInfoUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigation = MutableSharedFlow<AppScreen>()
    val navigation = _navigation.asSharedFlow()

    private val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    private var currentAppointment: Appointment? = null
    private var observeJob: Job? = null

    init {
        refreshAppointmentInfo()
    }

    fun refreshAppointmentInfo() {
        observeJob?.cancel()
        observeJob = viewModelScope.launch {
            observeAuthorizedWorkerId()
                .flatMapLatest { workerId ->
                    combine(
                        getAppointmentById(workerId, appScreen.id),
                        getPriceList(workerId),
                    ) { appointmentResult, priceListResult ->
                        Triple(workerId, appointmentResult, priceListResult)
                    }
                }.collect { payload ->
                    val workerId = payload.first
                    val appointmentResult = payload.second
                    val priceListResult = payload.third

                    if (appointmentResult !is Result.Success || priceListResult !is Result.Success) {
                        _uiState.update { it.copy(isLoading = true) }
                        return@collect
                    }

                    val appointment = appointmentResult.data
                    val servicesMap = priceListResult.data.associateBy { it.id }
                    val zone = ZoneId.systemDefault()
                    currentAppointment = appointment
                    val services = appointment.servicesIds.mapNotNull { id ->
                        servicesMap[id]?.let { service ->
                            service.name to "${service.price} ₽"
                        }
                    }

                    val totalPrice = appointment.servicesIds.sumOf { id ->
                        servicesMap[id]?.price ?: 0
                    }

                    getClient(workerId, appointment.clientId).collect { clientResult ->
                        if (clientResult !is Result.Success) {
                            return@collect
                        }

                        val client = clientResult.data
                        _uiState.value = AppointmentInfoUiState(
                            isLoading = false,
                            appointmentId = appointment.id,
                            clientId = client.id,
                            date = appointment.startTime.atZone(zone).format(dateFormatter),
                            time = "${appointment.startTime.atZone(zone).format(timeFormatter)}-${appointment.endTime.atZone(zone).format(timeFormatter)}",
                            services = services,
                            clientName = client.name,
                            clientPhone = formatPhone(client.phone),
                            comment = appointment.description,
                            status = if (appointment.cancelled) "Отменена" else "Подтверждена",
                            total = "$totalPrice ₽",
                            id = appointment.id,
                            cancelled = appointment.cancelled,
                        )
                    }

                }
        }
    }
    fun navigateTo(screen: AppScreen) {
        viewModelScope.launch {
            _navigation.emit(screen)
        }
    }

    fun onClientClick() {
        val clientId = uiState.value.clientId
        if (clientId.isNotBlank()) {
            navigateTo(AppScreen.ClientInfo(clientId))
        }
    }

    fun deleteAppointment() {
        val appointment = currentAppointment ?: return
        viewModelScope.launch {
            updateAppointment(
                appointment.copy(
                    cancelled = !appointment.cancelled,
                ),
            ).collect { result ->
                if (result is Result.Success) {
                    currentAppointment = appointment.copy(cancelled = !appointment.cancelled)
                    _uiState.update {
                        it.copy(
                            cancelled = !appointment.cancelled,
                            status = if (!appointment.cancelled) "Отменена" else "Подтверждена",
                        )
                    }
                    refreshAppointmentInfo()
                }
            }
        }    }

    @AssistedFactory
    interface Factory {
        fun create(appScreen: AppScreen.AppointmentInfo): AppointmentInfoViewModel
    }
    private fun formatPhone(value: String): String {
        val digits = value.filter { it.isDigit() }
        return if (digits.length == 11) {
            "${digits[0]} (${digits.substring(1, 4)}) ${digits.substring(4, 7)}-${digits.substring(7, 9)}-${digits.substring(9, 11)}"
        } else {
            value
        }
    }

}

