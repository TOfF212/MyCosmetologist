package com.hfad.mycosmetologist.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable


@Serializable
sealed interface AppScreen : NavKey {

    @Serializable
    data object Auth : AppScreen

    @Serializable
    data object Home : AppScreen
}