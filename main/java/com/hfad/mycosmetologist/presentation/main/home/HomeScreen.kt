package com.hfad.mycosmetologist.presentation.main.home

import android.app.DatePickerDialog
import android.content.res.Resources
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePickerDialog
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hfad.mycosmetologist.R
import com.hfad.mycosmetologist.presentation.main.home.components.AppointmentListElement
import com.hfad.mycosmetologist.presentation.main.home.components.DateCard
import com.hfad.mycosmetologist.presentation.main.home.components.HomeDatePicker
import com.hfad.mycosmetologist.presentation.main.home.entity.HomeEvent
import com.hfad.mycosmetologist.presentation.navigation.Navigator
import com.hfad.mycosmetologist.ui.theme.AppTheme
import com.hfad.mycosmetologist.ui.theme.ColorFamily

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), navigator: Navigator){

    val currentDay = viewModel.currentDay.collectAsState()
    val datePickerState = remember{mutableStateOf(false)}
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event){
                is HomeEvent.Navigate -> {
                    navigator.goTo(event.appScreen)
                }
                is HomeEvent.SelectDate -> {
                    datePickerState.value  = true

                }
                is HomeEvent.ShowError -> {

                }
            }
        }
    }

    if(datePickerState.value){
        HomeDatePicker(
            { viewModel.changeCurrentDate(it) },
            { datePickerState.value = false }
            )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(7.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        DateCard(
            onClick = {
                viewModel.triggerDatePicker()
            },
            clients = viewModel.getAppointmentsCountString(),
            revenue = viewModel.getRevenue(),
            date = viewModel.getStringDate()
        )

        Text(
            modifier = Modifier.padding(7.dp).alpha(0.92f).fillMaxWidth(),
            text = "Записи на сегодня",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 19.sp,
        )
        AppointmentListElement()
        AppointmentListElement()
        AppointmentListElement()
        AppointmentListElement()

    }
}