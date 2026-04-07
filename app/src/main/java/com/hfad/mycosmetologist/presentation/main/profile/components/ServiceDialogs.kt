package com.hfad.mycosmetologist.presentation.main.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun EditServiceDialog(
    name: String,
    price: String,
    durationMinutes: String,
    onNameChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onDurationChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
    onSave: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Редактирование услуги") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = name, onValueChange = onNameChange, label = { Text("Название") })
                OutlinedTextField(value = price, onValueChange = onPriceChange, label = { Text("Стоимость") })
                OutlinedTextField(value = durationMinutes, onValueChange = onDurationChange, label = { Text("Время (мин)") })
            }
        },
        confirmButton = {
            Button(onClick = onSave) {
                Text("Сохранить")
            }
        },
        dismissButton = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = onDismiss) {
                    Text("Отмена")
                }
                OutlinedButton(onClick = onDelete) {
                    Text("Удалить")
                }
            }
        },
    )
}

@Composable
fun AddServiceDialog(
    name: String,
    price: String,
    durationMinutes: String,
    onNameChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onDurationChange: (String) -> Unit,
    onCancel: () -> Unit,
    onSave: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text("Добавить услугу") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = name, onValueChange = onNameChange, label = { Text("Название") })
                OutlinedTextField(value = price, onValueChange = onPriceChange, label = { Text("Стоимость") })
                OutlinedTextField(value = durationMinutes, onValueChange = onDurationChange, label = { Text("Время (мин)") })
            }
        },
        confirmButton = {
            Button(onClick = onSave) {
                Text("Сохранить")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onCancel) {
                Text("Отменить")
            }
        },
    )
}
