package com.hfad.mycosmetologist.presentation.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.mycosmetologist.domain.entity.Service
import com.hfad.mycosmetologist.domain.useCase.service.CreateService
import com.hfad.mycosmetologist.domain.useCase.service.DeleteService
import com.hfad.mycosmetologist.domain.useCase.service.GetPriceList
import com.hfad.mycosmetologist.domain.useCase.service.UpdateService
import com.hfad.mycosmetologist.domain.useCase.worker.GetActualWorker
import com.hfad.mycosmetologist.domain.util.Result
import com.hfad.mycosmetologist.presentation.main.profile.entity.Profile
import com.hfad.mycosmetologist.presentation.main.profile.entity.ProfileEvent
import com.hfad.mycosmetologist.presentation.main.profile.entity.ProfileUiEvent
import com.hfad.mycosmetologist.presentation.main.profile.entity.ProfileUiState
import com.hfad.mycosmetologist.presentation.navigation.AppScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getActualWorker: GetActualWorker,
    private val getPriceList: GetPriceList,
    private val deleteServiceUseCase: DeleteService,
    private val createService: CreateService,
    private val updateService: UpdateService,
) : ViewModel() {

    private val _event = MutableSharedFlow<ProfileEvent>()
    val event = _event.asSharedFlow()

    private var currentWorkerId: String? = null

    val uiState: StateFlow<ProfileUiState> =
        getActualWorker()
            .flatMapLatest { workerResult ->
                when (workerResult) {
                    is Result.Success -> {
                        currentWorkerId = workerResult.data.id
                        getPriceList(workerResult.data.id)
                            .flatMapLatest { priceListResult ->
                                when (priceListResult) {
                                    is Result.Success -> {
                                        flowOf(
                                            ProfileUiState.Success(
                                                Profile(
                                                    priceList = priceListResult.data,
                                                    name = workerResult.data.name,
                                                    phone = workerResult.data.phone,
                                                    about = workerResult.data.about,
                                                    specialization = workerResult.data.specialization,
                                                    experience = "${workerResult.data.experience} лет",
                                                    email = workerResult.data.email,
                                                ),
                                            ),
                                        )
                                    }

                                    else -> flowOf(ProfileUiState.Loading)
                                }
                            }
                    }

                    else -> flowOf(ProfileUiState.Loading)
                }
            }.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                ProfileUiState.Loading,
            )

    fun onEvent(event: ProfileUiEvent) {
        when (event) {
            ProfileUiEvent.AddServiceClicked -> openAddDialog()
            is ProfileUiEvent.EditServiceClicked -> openEditDialog(event.service)
            is ProfileUiEvent.SaveNewService -> createService(event.name, event.price, event.durationMinutes)
            is ProfileUiEvent.SaveUpdatedService -> updateService(event.id, event.name, event.price, event.durationMinutes)
            is ProfileUiEvent.DeleteService -> deleteService(event.service)
            is ProfileUiEvent.NavigateTo -> navigateTo(event.appScreen)
        }
    }

    private fun openAddDialog() {
        viewModelScope.launch {
            _event.emit(ProfileEvent.OpenAddDialog)
        }
    }

    private fun openEditDialog(service: Service) {
        viewModelScope.launch {
            _event.emit(ProfileEvent.OpenEditDialog(service))
        }
    }

    private fun navigateTo(screen: AppScreen) {
        viewModelScope.launch {
            _event.emit(ProfileEvent.Navigate(screen))
        }
    }

    private fun closeDialogs() {
        viewModelScope.launch {
            _event.emit(ProfileEvent.CloseDialogs)
        }
    }

    private fun createService(
        name: String,
        price: String,
        durationMinutes: String,
    ) {
        val workerId = currentWorkerId ?: return
        val parsedPrice = price.toIntOrNull() ?: return
        val parsedDuration = durationMinutes.toIntOrNull() ?: return

        viewModelScope.launch {
            createService(
                Service(
                    id = UUID.randomUUID().toString(),
                    workerId = workerId,
                    name = name.trim(),
                    price = parsedPrice,
                    durationMinutes = parsedDuration,
                    description = "",
                ),
            ).collect {}
            closeDialogs()
        }
    }

    private fun updateService(
        id: String,
        name: String,
        price: String,
        durationMinutes: String,
    ) {
        val workerId = currentWorkerId ?: return
        val parsedPrice = price.toIntOrNull() ?: return
        val parsedDuration = durationMinutes.toIntOrNull() ?: return

        viewModelScope.launch {
            updateService(
                Service(
                    id = id,
                    workerId = workerId,
                    name = name.trim(),
                    price = parsedPrice,
                    durationMinutes = parsedDuration,
                    description = "",
                ),
            ).collect {}
            closeDialogs()
        }
    }

    private fun deleteService(service: Service) {
        viewModelScope.launch {
            deleteServiceUseCase(service).collect {}
            closeDialogs()
        }
    }
}
