package com.hfad.mycosmetologist.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppScreen : NavKey {
    @Serializable
    data object Auth : AppScreen

    @Serializable
    data object Home : AppScreen

    @Serializable
    data class AppointmentInfo(val id: String) : AppScreen

    @Serializable
    data object Clients : AppScreen

    @Serializable
    data object Profile : AppScreen
}
