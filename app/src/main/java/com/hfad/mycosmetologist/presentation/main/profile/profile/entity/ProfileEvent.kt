package com.hfad.mycosmetologist.presentation.main.profile.profile.entity

import com.hfad.mycosmetologist.domain.entity.Service
import com.hfad.mycosmetologist.presentation.navigation.AppScreen

sealed class ProfileEvent {
    data class Navigate(val screen: AppScreen) : ProfileEvent()

    data object OpenAddDialog : ProfileEvent()

    data class OpenEditDialog(val service: Service) : ProfileEvent()

    data object CloseDialogs : ProfileEvent()
}
