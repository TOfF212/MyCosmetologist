package com.hfad.mycosmetologist.presentation.main.profile.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.mycosmetologist.domain.useCase.appointment.GetPastAppointments
import com.hfad.mycosmetologist.domain.useCase.client.GetClientList
import com.hfad.mycosmetologist.domain.useCase.service.GetPriceList
import com.hfad.mycosmetologist.domain.useCase.session.ObserveAuthorizedWorkerId
import com.hfad.mycosmetologist.domain.util.Result
import com.hfad.mycosmetologist.presentation.main.profile.history.entity.HistoryUiState
import com.hfad.mycosmetologist.presentation.util.entity.PresentationAppointment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import java.time.Clock
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel
@Inject
constructor(
    observeAuthorizedWorkerId: ObserveAuthorizedWorkerId,
    getPastAppointments: GetPastAppointments,
    getPriceList: GetPriceList,
    getClientList: GetClientList,
    clock: Clock,
) : ViewModel() {
    val uiState: StateFlow<HistoryUiState> =
        observeAuthorizedWorkerId()
            .flatMapLatest { workerId ->
                combine(
                    getPastAppointments(workerId, Instant.now(clock)),
                    getPriceList(workerId),
                    getClientList(workerId),
                ) { past, priceList, clients ->
                    when {
                        past is Result.Success &&
                            priceList is Result.Success &&
                            clients is Result.Success -> {
                            val servicesMap = priceList.data.associateBy { it.id }
                            val clientsMap = clients.data.associateBy { it.id }
                            HistoryUiState.Success(
                                appointments =
                                    past.data
                                        .map {
                                            PresentationAppointment.toPresentationAppointment(
                                                it,
                                                servicesMap,
                                                clientsMap.get(it.clientId)!!.name,
                                            )
                                        }.sortedByDescending { it.startTime },
                            )
                        }

                        else -> HistoryUiState.Loading
                    }
                }
            }.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                HistoryUiState.Loading,
            )
}
