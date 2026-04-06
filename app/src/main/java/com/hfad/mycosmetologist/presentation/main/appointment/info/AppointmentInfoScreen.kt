package com.hfad.mycosmetologist.presentation.main.appointment.info

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfad.mycosmetologist.presentation.main.appointment.info.components.AppointmentInfoSectionCard
import com.hfad.mycosmetologist.presentation.main.appointment.info.components.AppointmentInfoServiceItem
import com.hfad.mycosmetologist.presentation.main.appointment.info.components.AppointmentInfoTopAppBar
import com.hfad.mycosmetologist.presentation.navigation.Navigator

@Composable
fun AppointmentInfoScreen(
    navigator: Navigator,
    viewModel: AppointmentInfoViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.navigation.collect { screen ->
            navigator.goTo(screen)
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Отменить запись") },
            text = { Text("Вы точно хотите удалить запись?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteAppointment()
                        showDeleteDialog = false
                    },
                ) {
                    Text("Удалить")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Оставить")
                }
            },
        )
    }

    Scaffold(
        topBar = {
            AppointmentInfoTopAppBar(
                onEditClick = {},
                onCancelClick = { showDeleteDialog = true },
            )
        },
    ) { paddingValues ->
        if (uiState.isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
            ) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 14.dp, vertical = 12.dp),
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                item {
                    AppointmentInfoSectionCard(
                        title = "Клиент",
                        value = "${uiState.clientName} · ${uiState.clientPhone}",
                        modifier = Modifier.clickable { viewModel.onClientClick() },
                    )
                }
                item {
                    AppointmentInfoSectionCard(
                        title = "Дата и время",
                        value = "${uiState.date} · ${uiState.time}",
                    )
                }
                item {
                    AppointmentInfoSectionCard(
                        title = "Статус",
                        value = uiState.status,
                    )
                }
                item {
                    Text(
                        text = "Услуги",
                        modifier = Modifier
                            .alpha(0.9f)
                            .padding(top = 4.dp),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 19.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                    )
                }
                items(uiState.services) { (title, price) ->
                    AppointmentInfoServiceItem(title = title, price = price)
                }
                item {
                    AppointmentInfoSectionCard(
                        title = "Комментарий",
                        value = uiState.comment,
                    )
                }
                item {
                    AppointmentInfoSectionCard(
                        title = "Итог",
                        value = uiState.total,
                    )
                }
            }
        }
    }
}

