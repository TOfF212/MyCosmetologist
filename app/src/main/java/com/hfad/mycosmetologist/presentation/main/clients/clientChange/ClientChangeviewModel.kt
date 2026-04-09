package com.hfad.mycosmetologist.presentation.main.clients.clientChange

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.mycosmetologist.domain.entity.Client
import com.hfad.mycosmetologist.domain.useCase.client.GetClient
import com.hfad.mycosmetologist.domain.useCase.client.UpdateClient
import com.hfad.mycosmetologist.domain.useCase.session.ObserveAuthorizedWorkerId
import com.hfad.mycosmetologist.domain.util.Result
import com.hfad.mycosmetologist.presentation.main.clients.clientChange.entity.ClientChangeEvent
import com.hfad.mycosmetologist.presentation.main.clients.clientChange.entity.ClientChangeUiState
import com.hfad.mycosmetologist.presentation.navigation.AppScreen
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = ClientChangeViewModel.Factory::class)
class ClientChangeViewModel @AssistedInject constructor(
    @Assisted private val appScreen: AppScreen.ClientChange,
    private val observeAuthorizedWorkerId: ObserveAuthorizedWorkerId,
    private val getClient: GetClient,
    private val updateClient: UpdateClient,
) : ViewModel() {

    private val clientId = appScreen.id

    private val _uiState = MutableStateFlow(ClientChangeUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<ClientChangeEvent>()
    val event = _event.asSharedFlow()

    private var workerId: String? = null

    init {
        loadClient()
    }

    private fun loadClient() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val currentWorkerId = observeAuthorizedWorkerId().firstOrNull()
            if (currentWorkerId == null) {
                _uiState.update { it.copy(isLoading = false) }
                _event.emit(ClientChangeEvent.ShowToast("Ошибка при сохранении"))
                return@launch
            }

            workerId = currentWorkerId
            getClient(currentWorkerId, clientId).collect { result ->
                when (result) {
                    is Result.Loading -> _uiState.update { it.copy(isLoading = true) }
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                name = result.data.name,
                                phone = result.data.phone,
                                about = result.data.about,
                            )
                        }
                    }

                    is Result.Error -> {
                        _uiState.update { it.copy(isLoading = false) }
                        Log.e("ClientChangeViewModel", result.exception.toString())
                        _event.emit(ClientChangeEvent.ShowToast("Ошибка при сохранении"))
                    }
                }
            }
        }
    }

    fun onPhoneChanged(newValue: String) {
        _uiState.update {
            it.copy(phone = newValue.filter { char -> char.isDigit() }.take(11))
        }
    }

    fun onNameChanged(newValue: String) {
        _uiState.update {
            it.copy(name = newValue.filter { char -> !char.isDigit() })
        }
    }

    fun onAboutChanged(newValue: String) {
        _uiState.update {
            it.copy(about = newValue)
        }
    }

    fun onSubmitClick() {
        val currentWorkerId = workerId
        if (currentWorkerId == null) {
            viewModelScope.launch {
                _event.emit(ClientChangeEvent.ShowToast("Ошибка при сохранении"))
            }
            return
        }

        if (uiState.value.phone.length != 11) {
            viewModelScope.launch {
                _event.emit(ClientChangeEvent.ShowToast("Введите корректный номер телефона"))
            }
            return
        }

        if (uiState.value.name.isBlank()) {
            viewModelScope.launch {
                _event.emit(ClientChangeEvent.ShowToast("Введите имя клиента"))
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            updateClient(
                Client(
                    id = clientId,
                    name = uiState.value.name,
                    phone = uiState.value.phone,
                    about = uiState.value.about,
                    workerId = currentWorkerId,
                ),
            ).collect { result ->
                when (result) {
                    is Result.Loading -> Unit
                    is Result.Success -> {
                        _uiState.update { it.copy(isLoading = false) }
                        _event.emit(ClientChangeEvent.NavigateBack)
                    }

                    is Result.Error -> {
                        _uiState.update { it.copy(isLoading = false) }
                        Log.e("ClientChangeViewModel", result.exception.toString())
                        _event.emit(ClientChangeEvent.ShowToast("Ошибка при сохранении"))
                    }
                }
            }
        }
    }


    @AssistedFactory
    interface Factory {
        fun create(appScreen: AppScreen.ClientChange): ClientChangeViewModel
    }
}
