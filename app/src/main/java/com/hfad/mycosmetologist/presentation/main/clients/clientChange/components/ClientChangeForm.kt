package com.hfad.mycosmetologist.presentation.main.clients.clientChange.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ClientChangeForm() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        ClientChangeTextField(
            value = "Анна Иванова",
            label = "Имя",
            singleLine = true,
        )

        ClientChangeTextField(
            value = "+7 999 123-45-67",
            label = "Телефон",
            singleLine = true,
        )

        ClientChangeTextField(
            value = "Чувствительная кожа, предпочитает вечерние записи.",
            label = "О клиенте",
            modifier = Modifier.height(120.dp),
            singleLine = false,
            maxLines = 4,
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            ),
        ) {
            Text(text = "Сохранить изменения", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun ClientChangeTextField(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean,
    maxLines: Int = 1,
) {
    OutlinedTextField(
        value = value,
        onValueChange = {},
        label = { Text(text = label) },
        modifier = modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
            unfocusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
            cursorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
            focusedLabelColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
        ),
        singleLine = singleLine,
        maxLines = maxLines,
        shape = RoundedCornerShape(12.dp),
    )
}
