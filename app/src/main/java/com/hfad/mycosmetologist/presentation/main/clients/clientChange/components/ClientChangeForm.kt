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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hfad.mycosmetologist.R

@Composable
fun ClientChangeForm(
    isLoading: Boolean,
    name: String,
    phone: String,
    about: String,
    onNameChanged: (String) -> Unit,
    onPhoneChanged: (String) -> Unit,
    onAboutChanged: (String) -> Unit,
    onSubmitClick: () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        ClientChangeTextField(
            value = name,
            onValueChange = onNameChanged,
            label = stringResource(R.string.auth_name),
            singleLine = true,
        )

        ClientChangeTextField(
            value = phone,
            onValueChange = onPhoneChanged,
            label = stringResource(R.string.auth_phone),
            singleLine = true,
        )

        ClientChangeTextField(
            value = about,
            onValueChange = onAboutChanged,
            label = stringResource(R.string.AboutClient),
            modifier = Modifier.height(120.dp),
            singleLine = false,
            maxLines = 4,
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onSubmitClick,
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            ),
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    strokeWidth = 2.dp,
                    modifier = Modifier.height(20.dp),
                )
            } else {
                Text(text = "Сохранить изменения", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun ClientChangeTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean,
    maxLines: Int = 1,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
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
