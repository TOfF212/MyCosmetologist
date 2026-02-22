package com.hfad.mycosmetologist.presentation.main.clients.clientInfo.entity

import com.hfad.mycosmetologist.presentation.navigation.AppScreen

sealed class ClientInfoEvent {

    data class ShowError(
        val message: String,
    ) : ClientInfoEvent()

    data class Navigate(
        val appScreen: AppScreen,
    ) : ClientInfoEvent()
}
