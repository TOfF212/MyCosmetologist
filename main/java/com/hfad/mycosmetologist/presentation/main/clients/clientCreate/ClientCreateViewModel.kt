package com.hfad.mycosmetologist.presentation.main.clients.clientCreate

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.identity.util.UUID
import com.hfad.mycosmetologist.domain.entity.Client
import com.hfad.mycosmetologist.domain.useCase.client.CreateClient
import com.hfad.mycosmetologist.domain.useCase.worker.GetActualWorker
import com.hfad.mycosmetologist.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientCreateViewModel @Inject constructor(
    private val getActualWorker: GetActualWorker,
    private val createClient: CreateClient
) : ViewModel() {
    private val _phone = MutableStateFlow("")
    val phone: StateFlow<String> = _phone

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _about = MutableStateFlow("")
    val about: StateFlow<String> = _about

    private val _events = MutableSharedFlow<CreateClientEvent>()
    val events = _events.asSharedFlow()

    fun onPhoneChanged(newValue: String) {
        _phone.update {
            newValue.filter { char -> char.isDigit() }
        }
    }

    fun onNameChanged(newValue: String) {
        _name.update {
            newValue.filter { char -> !char.isDigit() }
        }
    }

    fun onAboutChanged(newValue: String) {
        _about.update {
            newValue
        }
    }

    fun onSubmitClick() {
        viewModelScope.launch {
            getActualWorker().collect { actualWorkerResult ->
                when (actualWorkerResult) {
                    is Result.Error -> {}
                    is Result.Success -> {
                        createClient(
                            Client(
                                id = UUID.randomUUID().toString(),
                                name = name.value,
                                phone = phone.value,
                                about = about.value,
                                workerId = actualWorkerResult.data.id
                            ),
                        ).collect { result ->
                            when (result) {
                                is Result.Error -> {
                                    Log.e("ClientCreateViewModel", result.exception.toString())
                                    _events.emit(CreateClientEvent.ShowError(result.exception))
                                }

                                is Result.Loading -> {
                                }

                                is Result.Success<*> -> {
                                    _events.emit(CreateClientEvent.Navigate)
                                }
                            }
                        }
                    }

                    is Result.Loading -> {}
                }

            }

        }
    }
}
