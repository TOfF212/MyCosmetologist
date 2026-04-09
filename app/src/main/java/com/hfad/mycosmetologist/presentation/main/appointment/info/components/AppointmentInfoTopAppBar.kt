package com.hfad.mycosmetologist.presentation.main.appointment.info.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfad.mycosmetologist.R
import com.hfad.mycosmetologist.presentation.util.uiComponents.TopAppBar

@Composable
fun AppointmentInfoTopAppBar(
    onEditClick: () -> Unit,
    onCancelClick: () -> Unit,
    isCancelled: Boolean,
) {
    TopAppBar(headlineText = "Запись") {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Button(
                onClick = onEditClick,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    contentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                ),
            ) {
                Row() {
                    Icon(painter = painterResource(R.drawable.outline_edit_24), contentDescription = "Edit")

                    Text(text = "Изменить", fontWeight = FontWeight.Bold)
                }
            }
            Button(
                onClick = onCancelClick,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.6f),
                ),
            ) {
                Row() {
                    Icon(painter = if (isCancelled) painterResource(R.drawable.outline_autorenew_24) else  painterResource(R.drawable.outline_cancel_24), contentDescription = "Cancel")
                    Text(text = if (isCancelled) "Возобновить" else "Отменить", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
