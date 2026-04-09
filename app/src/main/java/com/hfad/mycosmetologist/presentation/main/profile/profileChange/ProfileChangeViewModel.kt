package com.hfad.mycosmetologist.presentation.main.profile.profileChange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.mycosmetologist.domain.entity.Worker
import com.hfad.mycosmetologist.domain.useCase.session.ObserveAuthorizedWorkerId
import com.hfad.mycosmetologist.domain.useCase.worker.GetWorker
import com.hfad.mycosmetologist.domain.useCase.worker.UpdateWorker
import com.hfad.mycosmetologist.domain.util.Result
import com.hfad.mycosmetologist.presentation.main.profile.profileChange.entity.ProfileChangeAction
import com.hfad.mycosmetologist.presentation.main.profile.profileChange.entity.ProfileChangeEvent
import com.hfad.mycosmetologist.presentation.main.profile.profileChange.entity.ProfileChangeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileChangeViewModel @Inject constructor(
    observeAuthorizedWorkerId: ObserveAuthorizedWorkerId,
    getWorker: GetWorker,
    private val updateWorker: UpdateWorker,
) : ViewModel() {

    private var workerToUpdate: Worker? = null

    private val _event = MutableSharedFlow<ProfileChangeEvent>()
    val event = _event.asSharedFlow()

    private val _state = MutableStateFlow(ProfileChangeUiState())
    val state: StateFlow<ProfileChangeUiState> = _state

    init {
        viewModelScope.launch {
            observeAuthorizedWorkerId()
                .flatMapLatest { workerId -> getWorker(workerId) }
                .collect { result ->
                    when (result) {
                        is Result.Success -> {
                            workerToUpdate = result.data

                            val shouldInitializeForm = _state.value.isLoading
                            if (shouldInitializeForm) {
                                _state.value =
                                    ProfileChangeUiState(
                                        name = result.data.name,
                                        phone = result.data.phone,
                                        email = result.data.email,
                                        specialization = result.data.specialization,
                                        experience = result.data.experience.toString(),
                                        about = result.data.about,
                                        isLoading = false,
                                    )
                            }
                        }

                        is Result.Error -> {
                            _state.update { it.copy(isLoading = false) }
                            _event.emit(
                                ProfileChangeEvent.Error(
                                    result.exception.message ?: "Не удалось загрузить профиль",
                                ),
                            )
                        }

                        Result.Loading -> _state.update { it.copy(isLoading = true) }
                    }
                }
        }
    }

    fun onAction(action: ProfileChangeAction) {
        when (action) {
            is ProfileChangeAction.OnNameChanged -> _state.update { it.copy(name = action.value) }
            is ProfileChangeAction.OnPhoneChanged -> _state.update { it.copy(phone = action.value) }
            is ProfileChangeAction.OnEmailChanged -> _state.update { it.copy(email = action.value) }
            is ProfileChangeAction.OnSpecializationChanged -> _state.update { it.copy(specialization = action.value) }
            is ProfileChangeAction.OnExperienceChanged -> _state.update { it.copy(experience = action.value.filter(Char::isDigit).take(2)) }
            is ProfileChangeAction.OnAboutChanged -> _state.update { it.copy(about = action.value) }
            ProfileChangeAction.OnSaveClicked -> saveProfile()
        }
    }

    private fun saveProfile() {
        val currentState = state.value
        val worker = workerToUpdate ?: return
        val experience = currentState.experience.toIntOrNull()

        if (currentState.name.isBlank() || currentState.phone.isBlank() || currentState.email.isBlank()) {
            viewModelScope.launch {
                _event.emit(ProfileChangeEvent.Error("Заполните имя, телефон и email"))
            }
            return
        }

        if (experience == null) {
            viewModelScope.launch {
                _event.emit(ProfileChangeEvent.Error("Укажите корректный стаж"))
            }
            return
        }

        viewModelScope.launch {
            updateWorker(
                worker.copy(
                    name = currentState.name.trim(),
                    phone = currentState.phone.trim(),
                    email = currentState.email.trim(),
                    specialization = currentState.specialization.trim(),
                    experience = experience,
                    about = currentState.about.trim(),
                ),
            ).collect { result ->
                when (result) {
                    is Result.Success -> _event.emit(ProfileChangeEvent.NavigateBack)
                    is Result.Error -> {
                        _event.emit(
                            ProfileChangeEvent.Error(
                                result.exception.message ?: "Не удалось сохранить профиль",
                            ),
                        )
                    }

                    Result.Loading -> Unit
                }
            }
        }
    }
}
