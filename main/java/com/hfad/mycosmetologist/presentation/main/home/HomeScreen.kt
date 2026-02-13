package com.hfad.mycosmetologist.presentation.main.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hfad.mycosmetologist.R
import com.hfad.mycosmetologist.presentation.main.home.components.DateCard
import com.hfad.mycosmetologist.presentation.main.home.components.HomeDatePicker
import com.hfad.mycosmetologist.presentation.main.home.entity.HomeEvent
import com.hfad.mycosmetologist.presentation.main.home.entity.HomeUiState
import com.hfad.mycosmetologist.presentation.navigation.AppScreen
import com.hfad.mycosmetologist.presentation.navigation.Navigator
import com.hfad.mycosmetologist.presentation.util.AppointmentListElement
import com.hfad.mycosmetologist.presentation.util.toMonthNameRes
import com.hfad.mycosmetologist.ui.theme.primaryLight

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigator: Navigator,
) {
    val currentDay = viewModel.currentDay.collectAsState()
    val datePickerState = remember { mutableStateOf(false) }
    val state by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is HomeEvent.Navigate -> {
                    navigator.goTo(event.appScreen)
                }

                is HomeEvent.SelectDate -> {
                    datePickerState.value = true
                }

                is HomeEvent.ShowError -> {
                }
            }
        }
    }

    if (datePickerState.value) {
        HomeDatePicker(
            { viewModel.changeCurrentDate(it) },
            { datePickerState.value = false },
        )
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(7.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DateCard(
            onClick = {
                viewModel.triggerDatePicker()
            },
            clients = viewModel.getAppointmentsCountString(),
            revenue = viewModel.getRevenue(),
            date = "${currentDay.value.dayOfMonth} ${
                stringResource(currentDay.value.month.toMonthNameRes())
            }",
        )


        when (val ui = state) {
            is HomeUiState.Success -> {
                LazyColumn {

                    item {
                        Text(
                            modifier = Modifier
                                .padding(7.dp)
                                .alpha(0.9f)
                                .fillMaxWidth(),
                            text = stringResource(R.string.appointmentsToday),
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 19.sp,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }

                    items((ui as HomeUiState.Success).currentAppointmentsList) { item ->
                        AppointmentListElement(
                            MaterialTheme.colorScheme.onPrimaryContainer,
                            item,
                            { viewModel.navigateTo(AppScreen.AppointmentInfo(item.id)) })
                    }

                    item {
                        Text(
                            modifier = Modifier
                                .padding(7.dp)
                                .alpha(0.9f)
                                .fillMaxWidth(),
                            text = stringResource(R.string.lastAppointments),
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 19.sp,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                    items((ui as HomeUiState.Success).pastAppointmentsList) { item ->
                        AppointmentListElement(
                            appointmentInfo = item,
                            onClick = { viewModel.navigateTo(AppScreen.AppointmentInfo(item.id)) })
                    }
                }
            }

            else -> {
                CircularProgressIndicator(color = primaryLight)
            }
        }
    }
}
