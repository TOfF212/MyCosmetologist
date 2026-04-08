package com.hfad.mycosmetologist.presentation.main.clients.clientChange.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.hfad.mycosmetologist.presentation.util.uiComponents.TopAppBar

@Composable
fun ClientChangeTopAppBar(clientId: String) {
    TopAppBar(headlineText = "Редактирование") {
        Text(
            text = "Клиент",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}
