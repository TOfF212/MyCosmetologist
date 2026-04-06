package com.hfad.mycosmetologist.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.hfad.mycosmetologist.presentation.main.appointment.change.AppointmentChangeScreen
import com.hfad.mycosmetologist.presentation.main.appointment.create.AppointmentCreateScreen
import com.hfad.mycosmetologist.presentation.main.appointment.create.AppointmentCreateViewModel
import com.hfad.mycosmetologist.presentation.main.appointment.info.AppointmentInfoScreen
import com.hfad.mycosmetologist.presentation.main.appointment.info.AppointmentInfoViewModel
import com.hfad.mycosmetologist.presentation.main.auth.AuthScreen
import com.hfad.mycosmetologist.presentation.main.clients.clientChange.ClientChangeScreen
import com.hfad.mycosmetologist.presentation.main.clients.clientCreate.ClientCreateScreen
import com.hfad.mycosmetologist.presentation.main.clients.clientInfo.ClientInfoScreen
import com.hfad.mycosmetologist.presentation.main.clients.clientInfo.ClientInfoViewModel
import com.hfad.mycosmetologist.presentation.main.clients.clientsList.ClientsList
import com.hfad.mycosmetologist.presentation.main.home.HomeScreen
import com.hfad.mycosmetologist.presentation.main.priceList.PriceListScreen
import com.hfad.mycosmetologist.presentation.main.profile.ProfileScreen
import com.hfad.mycosmetologist.presentation.main.splash.SplashScreen
import com.hfad.mycosmetologist.presentation.main.splash.SplashViewModel
import com.hfad.mycosmetologist.presentation.navigation.AppScreen
import com.hfad.mycosmetologist.presentation.navigation.Navigator
import com.hfad.mycosmetologist.presentation.navigation.ui.AppBottomNavigation
import com.hfad.mycosmetologist.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigator: Navigator

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                val startScreen by splashViewModel.startScreen.collectAsState()
                if (startScreen == null) {
                    SplashScreen()
                } else {
                    navigator.setRoot(startScreen!!)
                    val currentScreen = navigator.backStack.lastOrNull()
                    Scaffold(
                        bottomBar = {
                            if (currentScreen is AppScreen.Home || currentScreen is AppScreen.Profile || currentScreen is AppScreen.ClientsList) {
                                AppBottomNavigation(navigator)
                            }
                        }
                    ) { paddingValues ->
                        NavDisplay(
                            modifier = Modifier.padding(paddingValues),
                            backStack = navigator.backStack,
                            onBack = { navigator.goBack() },
                            entryProvider =
                                entryProvider {
                                    entry<AppScreen.Auth> {
                                        AuthScreen(navigator = navigator)
                                    }

                                    entry<AppScreen.Home> {
                                        HomeScreen(navigator = navigator)
                                    }

                                    entry<AppScreen.ClientsList> {
                                        ClientsList(navigator = navigator)
                                    }

                                    entry<AppScreen.AppointmentInfo> { key ->
                                        val viewModel = hiltViewModel<AppointmentInfoViewModel, AppointmentInfoViewModel.Factory>(
                                            creationCallback = { factory ->
                                                factory.create(key)
                                            }
                                        )
                                        AppointmentInfoScreen(navigator, viewModel)
                                    }

                                    entry<AppScreen.AppointmentChange> {
                                        AppointmentChangeScreen(it.id)
                                    }

                                    entry<AppScreen.AppointmentCreate> { key ->
                                        val viewModel =
                                            hiltViewModel<AppointmentCreateViewModel, AppointmentCreateViewModel.Factory>(
                                                creationCallback = { factory ->
                                                    factory.create(key)

                                                }
                                            )
                                        AppointmentCreateScreen(navigator, viewModel)
                                    }

                                    entry<AppScreen.ClientInfo> { key ->
                                        val viewModel =
                                            hiltViewModel<ClientInfoViewModel, ClientInfoViewModel.Factory>(
                                                creationCallback = { factory ->
                                                    factory.create(key)
                                                }
                                            )
                                        ClientInfoScreen(navigator, viewModel)
                                    }

                                    entry<AppScreen.ClientChange> {
                                        ClientChangeScreen(it.id)
                                    }

                                    entry<AppScreen.ClientCreate> {
                                        ClientCreateScreen(navigator)
                                    }

                                    entry<AppScreen.Profile> {
                                        ProfileScreen(navigator = navigator)
                                    }




                                    entry<AppScreen.PriceList> {
                                        PriceListScreen()
                                    }
                                },
                        )
                    }
                }
            }
        }
    }
}
