package com.hfad.mycosmetologist.presentation.main.clients.clientInfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hfad.mycosmetologist.presentation.main.clients.clientInfo.components.ClientInfoTopAppBar

@Composable
fun ClientInfoScreen(id: String) {
    Scaffold(
        topBar = {
            ClientInfoTopAppBar()
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 16.dp),
        ) {

        }
    }
}
