package com.hfad.mycosmetologist.presentation.navigation

import androidx.navigation3.runtime.entryProvider
import javax.inject.Inject
import androidx.navigation3.ui.NavDisplay

fun AppNavHost(navigator: Navigator) {
    NavDisplay(
        backStack = navigator.backStack,
        onBack = {navigator.goBack()},
        entryProvider = entryProvider {

            entry<AppScreen.Splash> {

            }

            entry<AppScreen.Auth> {

            }

            entry<AppScreen.Home> {

            }
        }
    )
}