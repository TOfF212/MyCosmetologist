package com.hfad.mycosmetologist.presentation.main.clients.clientsList.entity

sealed class ClientListUiState {

    data class Success(val clientList: List<Client>) : ClientListUiState()

    object Loading : ClientListUiState()

    data class Error(val message: String) : ClientListUiState()
}
