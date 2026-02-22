package com.hfad.mycosmetologist.presentation.main.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hfad.mycosmetologist.domain.useCase.worker.IsWorkerAuthorized
import com.hfad.mycosmetologist.domain.util.Result
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
        private val isWorkerAuthorized: IsWorkerAuthorized,
    ) : ViewModel() {
        val startScreen =
            isWorkerAuthorized()
                .map { result ->
                    when (result) {
                        is Result.Success -> {
                            if (result.data) AppScreen.Home else AppScreen.Auth
                        } else -> null
                    }
                }.stateIn(
                    viewModelScope,
                    SharingStarted.WhileSubscribed(5000),
                    null,
                )
    }
