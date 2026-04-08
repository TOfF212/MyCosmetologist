package com.hfad.mycosmetologist.presentation.main.profile.profile.entity


import com.hfad.mycosmetologist.domain.entity.Service
import com.hfad.mycosmetologist.presentation.navigation.AppScreen

sealed class ProfileUiEvent {
    data object AddServiceClicked : ProfileUiEvent()

    data class EditServiceClicked(val service: Service) : ProfileUiEvent()

    data class SaveNewService(
        val name: String,
        val price: String,
        val durationMinutes: String,
    ) : ProfileUiEvent()

    data class SaveUpdatedService(
        val id: String,
        val name: String,
        val price: String,
        val durationMinutes: String,
    ) : ProfileUiEvent()

    data class DeleteService(val service: Service) : ProfileUiEvent()


    data class NavigateTo(val appScreen: AppScreen): ProfileUiEvent()
}
