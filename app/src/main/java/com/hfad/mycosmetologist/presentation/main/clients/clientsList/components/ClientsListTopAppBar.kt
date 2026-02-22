package com.hfad.mycosmetologist.presentation.main.clients.clientsList.components

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import com.hfad.mycosmetologist.presentation.main.clients.clientsList.entity.Client
import com.hfad.mycosmetologist.presentation.util.uiComponents.TopAppBar

@Composable
fun ClientsListTopAppBar(
    clientList: List<Client>,
    onClick: (String) -> Unit
) {
    TopAppBar(
        headlineText = "Клиенты"
    ) {
        ClientsListSearchBar(
            TextFieldState(""),
            clientList,
            { id -> onClick(id) },

            )
    }
}

