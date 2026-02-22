package com.hfad.mycosmetologist.presentation.main.clients.clientsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.mycosmetologist.domain.useCase.client.GetClientList
import com.hfad.mycosmetologist.domain.useCase.worker.GetActualWorker
import com.hfad.mycosmetologist.domain.util.Result
import com.hfad.mycosmetologist.presentation.main.clients.clientsList.entity.Client
import com.hfad.mycosmetologist.presentation.main.clients.clientsList.entity.ClientListUiState
import com.hfad.mycosmetologist.presentation.navigation.AppScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientListViewModel @Inject constructor(
    private val getActualWorker: GetActualWorker,
    private val getClientList: GetClientList
) : ViewModel() {


    private val _navigate = MutableSharedFlow<AppScreen>()
    val navigate = _navigate.asSharedFlow()

    fun navigateTo(screen: AppScreen) {
        viewModelScope.launch {
            _navigate.emit(screen)
        }
    }


    val uiState: StateFlow<ClientListUiState> =
        getActualWorker()
            .flatMapLatest { result ->
                when (result) {
                    is Result.Success -> {
                        buildUiState(result.data.id)
                    }

                    else -> flowOf(ClientListUiState.Loading)
                }
            }.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                ClientListUiState.Loading,
            )

    private fun buildUiState(workerId: String): Flow<ClientListUiState> =
        getClientList(workerId)
            .flatMapLatest { result ->
                when (result) {
                    is Result.Success -> {
                        flowOf(
                            ClientListUiState.Success(
                                clientList =
                                    result.data.map {
                                        Client(
                                            it.id,
                                            it.name,
                                            "${it.phone.get(0)} (${
                                                it.phone.subSequence(
                                                    1,
                                                    4
                                                )
                                            }) ${it.phone.subSequence(4, 7)}-${
                                                it.phone.subSequence(
                                                    7,
                                                    9
                                                )
                                            }-${it.phone.subSequence(9, 11)}"
                                        )
                                    }
                            ))
                    }

                    else -> flowOf(ClientListUiState.Loading)
                }

            }
}
