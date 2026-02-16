package com.hfad.mycosmetologist.presentation.main.clients.clientInfo.entity

sealed class ClientInfoUiState {

    data class Success(val client: ClientInfo) : ClientInfoUiState()

    object Loading : ClientInfoUiState()

}
