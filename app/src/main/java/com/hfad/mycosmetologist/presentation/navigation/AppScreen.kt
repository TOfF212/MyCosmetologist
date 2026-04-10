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
    data class AppointmentChange(val id: String) : AppScreen

    @Serializable
    data class AppointmentCreate(val clientId: String) : AppScreen

    @Serializable
    data object ClientsList : AppScreen

    @Serializable
    data class ClientInfo(val id: String) : AppScreen

    @Serializable
    data class ClientChange(val id: String) : AppScreen

    @Serializable
    object ClientCreate : AppScreen

    @Serializable
    data object Profile : AppScreen

    @Serializable
    data object ProfileChange : AppScreen
    @Serializable
    data object PriceList : AppScreen

    @Serializable
    data object History : AppScreen
}
