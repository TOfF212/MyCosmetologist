package com.hfad.mycosmetologist.presentation.util.uiComponents

import android.icu.util.Calendar
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTimePicker(
    onConfirm: (hour: Int, minutes: Int) -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        val currentTime = Calendar.getInstance()

        val timePickerState = rememberTimePickerState(
            initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
            initialMinute = currentTime.get(Calendar.MINUTE),
            is24Hour = true,
        )
        timePickerState.selection.value
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TimeInput(
                state = timePickerState,
            )
            Button(onClick = onDismiss) {
                Text("Отменить")
            }
            Button(onClick = {
                onConfirm(timePickerState.hour, timePickerState.minute)
            }) {
                Text("Сохранить")
            }
        }
    }
}
