package com.hfad.mycosmetologist.presentation.main.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.identity.util.UUID
import com.hfad.mycosmetologist.domain.entity.Worker
import com.hfad.mycosmetologist.domain.useCase.worker.CreateWorker
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
class AuthViewModel
    @Inject
    constructor(
        private val createWorker: CreateWorker,
    ) : ViewModel() {
        private val _phone = MutableStateFlow("")
        val phone: StateFlow<String> = _phone

        private val _name = MutableStateFlow("")
        val name: StateFlow<String> = _name

        private val _password = MutableStateFlow("")
        val password: StateFlow<String> = _password

        private val _events = MutableSharedFlow<AuthEvent>()
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

        fun onPasswordChanged(newValue: String) {
            _password.update {
                newValue
            }
        }

        fun onSubmitClick() {
            viewModelScope.launch {
                createWorker(
                    Worker(
                        id = UUID.randomUUID().toString(),
                        name = name.value,
                        phone = phone.value,
                        about = "",
                        password = password.value,
                    ),
                ).collect { result ->
                    when (result) {
                        is Result.Error -> {
                            Log.e("AuthViewModel", result.exception.toString())
                            _events.emit(AuthEvent.ShowError)
                        }
                        is Result.Loading -> {
                        }
                        is Result.Success<*> -> {
                            _events.emit(AuthEvent.NavigateHome)
                        }
                    }
                }
            }
        }
    }
