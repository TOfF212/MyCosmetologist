package com.hfad.mycosmetologist.presentation.main.profile.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hfad.mycosmetologist.R
import com.hfad.mycosmetologist.presentation.main.profile.history.entity.HistoryUiState
import com.hfad.mycosmetologist.presentation.navigation.AppScreen
import com.hfad.mycosmetologist.presentation.navigation.Navigator
import com.hfad.mycosmetologist.presentation.util.uiComponents.AppointmentListElement
import com.hfad.mycosmetologist.presentation.util.uiComponents.TopAppBar

@Composable
fun HistoryScreen(
    navigator: Navigator,
    viewModel: HistoryViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(headlineText = "История записей") {
            Button(onClick = { navigator.goTo(AppScreen.Profile) }) {
                Text(text = "Назад")
            }
        }

        when (val ui = state) {
            is HistoryUiState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    CircularProgressIndicator()
                }
            }

            is HistoryUiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                ) {
                    items(ui.appointments) { item ->
                        AppointmentListElement(
                            backgroundColor = if (item.cancelled)
                                MaterialTheme.colorScheme.scrim.copy(alpha = 0.3f)
                             else
                                Color(0xFFD1C4E9)
                            ,
                            appointmentInfo = item,
                            onClick = { navigator.goTo(AppScreen.AppointmentInfo(item.id)) },
                        )
                    }
                }
            }
        }
    }
}
