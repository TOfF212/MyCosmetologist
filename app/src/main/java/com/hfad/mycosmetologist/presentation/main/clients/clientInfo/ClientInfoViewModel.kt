package com.hfad.mycosmetologist.presentation.main.clients.clientInfo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.mycosmetologist.domain.useCase.appointment.GetAppointmentsByClient
import com.hfad.mycosmetologist.domain.useCase.client.GetClient
import com.hfad.mycosmetologist.domain.useCase.service.GetPriceList
import com.hfad.mycosmetologist.domain.useCase.session.ObserveAuthorizedWorkerId
import com.hfad.mycosmetologist.domain.useCase.worker.GetActualWorker
import com.hfad.mycosmetologist.domain.util.Result
import com.hfad.mycosmetologist.presentation.main.clients.clientInfo.entity.ClientInfo
import com.hfad.mycosmetologist.presentation.main.clients.clientInfo.entity.ClientInfoEvent
import com.hfad.mycosmetologist.presentation.main.clients.clientInfo.entity.ClientInfoUiState
import com.hfad.mycosmetologist.presentation.navigation.AppScreen
import com.hfad.mycosmetologist.presentation.util.entity.PresentationAppointment
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.Clock

@HiltViewModel(assistedFactory = ClientInfoViewModel.Factory::class)
class ClientInfoViewModel @AssistedInject constructor(
    @Assisted private val appScreen: AppScreen.ClientInfo,
    private val getClient: GetClient,
    private val observeAuthorizedWorkerId: ObserveAuthorizedWorkerId, private val getAppointmentsByClient: GetAppointmentsByClient,
    private val clock: Clock,
    private val getPriceList: GetPriceList
) : ViewModel() {

    private val clientId = appScreen.id
    private val _event = MutableSharedFlow<ClientInfoEvent>()
    val event = _event.asSharedFlow()

    val uiState: StateFlow<ClientInfoUiState> =        observeAuthorizedWorkerId()
        .flatMapLatest { workerId ->
            buildUiState(workerId)
            }.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                ClientInfoUiState.Loading,
            )

    private fun buildUiState(workerId: String): Flow<ClientInfoUiState> =
        combine(
            getClient(workerId, clientId),
            getPriceList(workerId),
            getAppointmentsByClient(workerId, clientId)
        ) { client, priceList, appsList ->
            when {
                (client is Result.Success && appsList is Result.Success && priceList is Result.Success) -> {
                    val servicesMap = priceList.data.associateBy { it.id }

                    ClientInfoUiState.Success(
                        ClientInfo(
                            id = client.data.id,
                            name = client.data.name,
                            phone = "${client.data.phone.get(0)} (${
                                client.data.phone.subSequence(
                                    1,
                                    4
                                )
                            }) ${client.data.phone.subSequence(4, 7)}-${
                                client.data.phone.subSequence(
                                    7,
                                    9
                                )
                            }-${client.data.phone.subSequence(9, 11)}",
                            about = client.data.about,
                            currentList = appsList.data.filter { it.endTime > clock.instant() }.map {
                                PresentationAppointment.toPresentationAppointment(
                                    it,
                                    servicesMap,
                                    client.data.name
                                )
                            },
                            pastList = appsList.data.filter { it.endTime <= clock.instant() }.map {
                                PresentationAppointment.toPresentationAppointment(
                                    it,
                                    servicesMap,
                                    client.data.name
                                )
                            }
                        )
                    )
                }

                (client is Result.Error) -> {
                    _event.emit(ClientInfoEvent.ShowError(client.exception.message ?: "Error client"))
                    Log.e("ClientInfoViewModel", client.exception.toString())
                    ClientInfoUiState.Loading
                }

                (appsList is Result.Error) -> {
                    _event.emit(ClientInfoEvent.ShowError(appsList.exception.message ?: "Error appointment list"))
                    Log.e("ClientInfoViewModel", appsList.exception.toString())
                    ClientInfoUiState.Loading
                }

                (priceList is Result.Error) -> {
                    _event.emit(ClientInfoEvent.ShowError(priceList.exception.message ?: "Error price list"))
                    Log.e("ClientInfoViewModel", priceList.exception.toString())
                    ClientInfoUiState.Loading
                }

                else -> {
                    ClientInfoUiState.Loading
                }
            }
        }

    fun navigateTo(screen: AppScreen) {
        viewModelScope.launch {
            _event.emit(ClientInfoEvent.Navigate(screen))
        }
    }


    @AssistedFactory
    interface Factory {
        fun create(appScreen: AppScreen.ClientInfo): ClientInfoViewModel
    }
}
