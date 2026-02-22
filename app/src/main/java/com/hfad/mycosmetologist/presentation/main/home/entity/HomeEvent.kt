package com.hfad.mycosmetologist.presentation.main.home.entity

import com.hfad.mycosmetologist.presentation.navigation.AppScreen

sealed class HomeEvent {
    object SelectDate : HomeEvent()

    data class ShowError(
        val message: String,
    ) : HomeEvent()

    data class Navigate(
        val appScreen: AppScreen,
    ) : HomeEvent()
}
