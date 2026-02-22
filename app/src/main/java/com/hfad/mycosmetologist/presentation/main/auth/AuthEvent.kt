package com.hfad.mycosmetologist.presentation.main.auth

sealed interface AuthEvent {
    object NavigateHome : AuthEvent

    object ShowError : AuthEvent
}
