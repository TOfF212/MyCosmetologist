package com.hfad.mycosmetologist.presentation.util.uiComponents

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDatePicker(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        colors = DatePickerDefaults.colors(
            selectedDayContainerColor = MaterialTheme.colorScheme.onPrimary,
            selectedDayContentColor = MaterialTheme.colorScheme.onPrimary,
            selectedYearContentColor = MaterialTheme.colorScheme.onPrimary,
            todayDateBorderColor = MaterialTheme.colorScheme.onPrimary,
            dayInSelectionRangeContainerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f)
        ),
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            TextButton({
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text(

                    text = "OK", color = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismiss()
            }) {
                Text("Cancel", color = MaterialTheme.colorScheme.onPrimary)
            }
        },
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                selectedDayContainerColor = MaterialTheme.colorScheme.primaryContainer,
                selectedDayContentColor = MaterialTheme.colorScheme.onPrimary,
                selectedYearContainerColor = MaterialTheme.colorScheme.primaryContainer,
                selectedYearContentColor = MaterialTheme.colorScheme.onPrimary,
                todayDateBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                todayContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
            )
        )
    }
}
