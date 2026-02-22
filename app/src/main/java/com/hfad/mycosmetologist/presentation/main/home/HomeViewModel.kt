package com.hfad.mycosmetologist.presentation.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.mycosmetologist.domain.useCase.appointment.GetAppointmentsByDate
import com.hfad.mycosmetologist.domain.useCase.appointment.GetPastAppointments
import com.hfad.mycosmetologist.domain.useCase.client.GetClientList
import com.hfad.mycosmetologist.domain.useCase.service.GetPriceList
import com.hfad.mycosmetologist.domain.useCase.worker.GetActualWorker
import com.hfad.mycosmetologist.domain.util.Result
import com.hfad.mycosmetologist.presentation.main.home.entity.HomeEvent
import com.hfad.mycosmetologist.presentation.main.home.entity.HomeUiState
import com.hfad.mycosmetologist.presentation.navigation.AppScreen
import com.hfad.mycosmetologist.presentation.util.entity.PresentationAppointment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val getAppointmentsByDate: GetAppointmentsByDate,
    private val getPastAppointments: GetPastAppointments,
    private val getClientList: GetClientList,
    private val getPriceList: GetPriceList,
    private val getActualWorker: GetActualWorker,
    private val clock: Clock,
) : ViewModel() {
    private val _currentDay = MutableStateFlow(LocalDate.now(clock))
    val currentDay: StateFlow<LocalDate> get() = _currentDay

    private val _event = MutableSharedFlow<HomeEvent>()
    val event = _event.asSharedFlow()

    val uiState: StateFlow<HomeUiState> =
        getActualWorker()
            .flatMapLatest { result ->
                when (result) {
                    is Result.Success -> buildUiState(result.data.id)
                    else -> flowOf(HomeUiState.AllLoading)
                }
            }.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                HomeUiState.AllLoading,
            )

    private fun buildUiState(workerId: String): Flow<HomeUiState> =
        currentDay.flatMapLatest { date ->
            val startOfDay = date.atStartOfDay(clock.zone).toInstant()
            combine(
                getAppointmentsByDate(workerId, startOfDay),
                getPastAppointments(workerId, Instant.now(clock)),
                getPriceList(workerId),
                getClientList(workerId),
            ) { current, past, priceList, clientList ->
                when {
                    current is Result.Success &&
                        past is Result.Success &&
                        priceList is Result.Success &&
                        clientList is Result.Success -> {
                        val servicesMap = priceList.data.associateBy { it.id }
                        val clientsMap = clientList.data.associateBy { it.id }
                        HomeUiState.Success(
                            currentAppointmentsList =
                                current.data.map {
                                    PresentationAppointment.toPresentationAppointment(
                                        it,
                                        servicesMap,
                                        clientsMap.get(it.clientId)!!.name
                                    )
                                },
                            pastAppointmentsList =
                                past.data.map {
                                    PresentationAppointment.toPresentationAppointment(
                                        it,
                                        servicesMap,
                                        clientsMap.get(it.clientId)!!.name
                                    )
                                },
                        )
                    }

                    else -> HomeUiState.AllLoading
                }
            }
        }


    fun triggerDatePicker() {
        viewModelScope.launch {
            _event.emit(HomeEvent.SelectDate)
        }
    }

    fun changeCurrentDate(newDate: Long?) {
        if (newDate == null) return
        _currentDay.value = LocalDate.ofInstant(Instant.ofEpochMilli(newDate), clock.zone)
    }


    fun getRevenue(): String {
        var res = 0
        if (uiState.value is HomeUiState.Success) {
            (uiState.value as HomeUiState.Success).currentAppointmentsList.forEach { res += it.profit.toInt() }
        }
        return res.toString()
    }

    fun getAppointmentsCountString(): String {
        var res = 0
        if (uiState.value is HomeUiState.Success) {
            res = (uiState.value as HomeUiState.Success).currentAppointmentsList.size
        }
        return res.toString()
    }

    fun navigateTo(screen: AppScreen) {
        viewModelScope.launch {
            _event.emit(HomeEvent.Navigate(screen))
        }
    }
}
