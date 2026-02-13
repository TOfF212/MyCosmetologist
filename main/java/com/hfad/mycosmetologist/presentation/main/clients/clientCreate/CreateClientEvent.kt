package com.hfad.mycosmetologist.presentation.main.clients.clientCreate

sealed class CreateClientEvent {
    object Navigate : CreateClientEvent()

    data class ShowError(val e: Throwable) : CreateClientEvent()
}
