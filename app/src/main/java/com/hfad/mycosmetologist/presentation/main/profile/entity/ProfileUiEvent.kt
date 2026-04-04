package com.hfad.mycosmetologist.presentation.main.profile.entity

import com.hfad.mycosmetologist.presentation.navigation.AppScreen

sealed class ProfileUiEvent {

    data class DeleteService(val id: String): ProfileUiEvent()

    data class Navigate(val appScreen: AppScreen): ProfileUiEvent()

    object AddService: ProfileUiEvent()
}
