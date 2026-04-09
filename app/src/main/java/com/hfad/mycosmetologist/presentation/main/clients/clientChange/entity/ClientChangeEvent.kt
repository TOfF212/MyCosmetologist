package com.hfad.mycosmetologist.presentation.main.clients.clientChange.entity

sealed class ClientChangeEvent {
    data object NavigateBack : ClientChangeEvent()

    data class ShowToast(val message: String) : ClientChangeEvent()
}
