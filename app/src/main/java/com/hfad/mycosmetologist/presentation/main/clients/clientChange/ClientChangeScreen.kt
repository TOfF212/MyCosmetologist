package com.hfad.mycosmetologist.presentation.main.clients.clientChange
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hfad.mycosmetologist.presentation.main.clients.clientChange.components.ClientChangeForm
import com.hfad.mycosmetologist.presentation.main.clients.clientChange.components.ClientChangeTopAppBar

@Composable
fun ClientChangeScreen(id: String) {
    Scaffold(
        topBar = { ClientChangeTopAppBar(clientId = id) },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 16.dp),
        ) {
            ClientChangeForm()
        }
    }
}
