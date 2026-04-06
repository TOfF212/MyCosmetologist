package com.hfad.mycosmetologist.presentation.main.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.mycosmetologist.domain.repository.SessionRepository
import com.hfad.mycosmetologist.domain.session.SessionState
import com.hfad.mycosmetologist.presentation.navigation.AppScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SplashViewModel
@Inject
constructor(
    private val sessionRepository: SessionRepository,
) : ViewModel() {
    val startScreen =
        sessionRepository
            .observeSession()
            .map { session ->
                when (session) {
                    is SessionState.Authorized -> AppScreen.Home
                    SessionState.Unauthorized -> AppScreen.Auth
                }
            }.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                null,
            )
}
