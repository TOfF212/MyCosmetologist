package com.hfad.mycosmetologist.presentation.navigation

sealed interface AppScreen {
    data object Splash : AppScreen
    data object Auth: AppScreen
    data object Home: AppScreen

}