package com.hfad.mycosmetologist.presentation.main.appointment.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hfad.mycosmetologist.R
import com.hfad.mycosmetologist.presentation.main.appointment.create.entity.CreateAppointmentAction
import com.hfad.mycosmetologist.presentation.navigation.Navigator
import com.hfad.mycosmetologist.presentation.util.toMonthNameRes
import com.hfad.mycosmetologist.presentation.util.uiComponents.AppointmentListElement
import com.hfad.mycosmetologist.presentation.util.uiComponents.TopAppBar


@Composable
fun AppointmentCreateScreen(
    navigator: Navigator,
    viewModel: AppointmentCreateViewModel
) {

    val uiState by viewModel.state.collectAsStateWithLifecycle()
    var aboutField by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(uiState.about))
    }
    Scaffold(
        topBar = {
            TopAppBar("Создание записи", {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f),
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier.alpha(0.65f),
                            text = "Клиент",
                            fontSize = 14.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            modifier = Modifier.alpha(0.95f),
                            text = uiState.clientName,
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            modifier = Modifier.alpha(0.65f),
                            text = uiState.clientNumber,
                            fontSize = 16.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            })
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)

        ) {
            item {
                Text(
                    modifier = Modifier
                        .alpha(0.9f)
                        .fillMaxWidth()
                        .padding(vertical = 7.dp),
                    text = "Дата",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 19.sp,
                )
            }

            item {
                Button(
                    onClick = { viewModel.onDateClick() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 1.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_calendar_today_24),
                        contentDescription = "Date"
                    )
                    Text(
                        modifier = Modifier
                            .alpha(0.9f)
                            .fillMaxWidth()
                            .padding(vertical = 7.dp, horizontal = 7.dp),
                        text = uiState.selectedDate.toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 19.sp,
                    )
                }
            }
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f),
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier
                                .alpha(0.9f)
                                .fillMaxWidth()
                                .padding(vertical = 7.dp),
                            text = "Записи на ${uiState.selectedDate.dayOfMonth} ${stringResource(uiState.selectedDate.month.toMonthNameRes())}",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 17.sp,
                            textAlign = TextAlign.Center
                        )
                        if (uiState.appointmentList.isEmpty()) {
                            Text(
                                modifier = Modifier
                                    .alpha(0.6f)
                                    .fillMaxWidth()
                                    .padding(vertical = 7.dp),
                                text = "В этот день записей нет",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 17.sp,
                                textAlign = TextAlign.Center
                            )
                        } else {
                            LazyColumn(Modifier.padding(1.dp)) {
                                items(uiState.appointmentList.filter { !it.cancelled }) { item ->
                                    AppointmentListElement(appointmentInfo = item, onClick = {})
                                }
                            }
                        }
                    }


                }
            }

            item {
                Text(
                    modifier = Modifier
                        .alpha(0.9f)
                        .fillMaxWidth()
                        .padding(vertical = 7.dp),
                    text = "Начало записи",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 19.sp,
                )
            }
            item {
                Button(
                    onClick = { viewModel.onDateClick() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_access_time_24),
                        contentDescription = "Time"
                    )
                    Text(
                        modifier = Modifier
                            .alpha(0.9f)
                            .fillMaxWidth()
                            .padding(vertical = 7.dp, horizontal = 7.dp),
                        text = (uiState.endTime ?: "--:--").toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 19.sp,
                    )
                }
            }

            item {
                Text(
                    modifier = Modifier
                        .alpha(0.9f)
                        .fillMaxWidth()
                        .padding(vertical = 7.dp),
                    text = "Конец записи",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 19.sp,
                )
            }

            item {
                Button(
                    onClick = { viewModel.onDateClick() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_access_time_24),
                        contentDescription = "Time"
                    )
                    Text(
                        modifier = Modifier
                            .alpha(0.9f)
                            .fillMaxWidth()
                            .padding(vertical = 7.dp, horizontal = 7.dp),
                        text = (uiState.endTime ?: "--:--").toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 19.sp,
                    )
                }
            }


            item {
                Text(
                    modifier = Modifier
                        .alpha(0.9f)
                        .fillMaxWidth()
                        .padding(vertical = 7.dp),
                    text = "Услуги",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 19.sp,
                )
            }


            item {
                Text(
                    modifier = Modifier
                        .alpha(0.9f)
                        .fillMaxWidth()
                        .padding(vertical = 7.dp),
                    text = "Описание",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 19.sp,
                )
            }

            item {
                TextField(
                    value = aboutField,
                    onValueChange = { newValue ->
                        aboutField = newValue
                        viewModel.onAction(
                            CreateAppointmentAction.OnAboutChanged(newValue.text)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                        cursorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    ),
                    shape = RoundedCornerShape(14.dp),
                    maxLines = 4,
                    placeholder = {
                        Text("Описание записи...")
                    },

                    )
            }


            item {
                Button(
                    modifier = Modifier.padding(end = 7.dp),
                    onClick = { viewModel.onAction(CreateAppointmentAction.OnSaveClicked) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .alpha(0.9f)
                            .fillMaxWidth()
                            .padding(vertical = 7.dp, horizontal = 7.dp),
                        text = "Создать Запись",
                        fontWeight = FontWeight.Bold,
                        fontSize = 19.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

        }
    }
}
