package com.hfad.mycosmetologist.presentation.main.clients.clientCreate

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.identity.util.UUID
import com.hfad.mycosmetologist.domain.entity.Client
import com.hfad.mycosmetologist.domain.useCase.client.CreateClient
import com.hfad.mycosmetologist.domain.useCase.session.ObserveAuthorizedWorkerId
import com.hfad.mycosmetologist.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientCreateViewModel @Inject constructor(
    private val observeAuthorizedWorkerId: ObserveAuthorizedWorkerId,
    private val createClient: CreateClient,
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
            newValue.filter { char -> char.isDigit() }.take(11)
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
        if (phone.value.length != 11) {
            viewModelScope.launch {
                _events.emit(CreateClientEvent.ShowError(Exception("The number length is uncorrected")))
            }
            return
        }
        viewModelScope.launch {
            val workerId = observeAuthorizedWorkerId().firstOrNull()
            if (workerId == null) {
                _events.emit(CreateClientEvent.ShowError(Exception("Worker is unauthorized")))
                return@launch
            }
            createClient(
                Client(
                    id = UUID.randomUUID().toString(),
                    name = name.value,
                    phone = phone.value,
                    about = about.value,
                    workerId = workerId,
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
    }
}
