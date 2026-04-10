package com.hfad.mycosmetologist.presentation.main.clients.clientInfo

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.hfad.mycosmetologist.R
import com.hfad.mycosmetologist.presentation.main.clients.clientInfo.components.ClientInfoTopAppBar
import com.hfad.mycosmetologist.presentation.main.clients.clientInfo.entity.ClientInfoEvent
import com.hfad.mycosmetologist.presentation.main.clients.clientInfo.entity.ClientInfoUiState
import com.hfad.mycosmetologist.presentation.navigation.AppScreen
import com.hfad.mycosmetologist.presentation.navigation.Navigator
import com.hfad.mycosmetologist.presentation.util.toMonthNameRes
import com.hfad.mycosmetologist.presentation.util.uiComponents.AppointmentListElement
import com.hfad.mycosmetologist.ui.theme.primaryLight

@Composable
fun ClientInfoScreen(navigator: Navigator, viewModel: ClientInfoViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState.collectAsState()

    val event = viewModel.event

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.refreshClientInfo()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(Unit) {
        event.collect { result ->
            when (result) {
                is ClientInfoEvent.Navigate -> {
                    navigator.goTo(result.appScreen)
                }

                is ClientInfoEvent.ShowError -> {
                    Toast
                        .makeText(
                            context,
                            result.message,
                            Toast.LENGTH_SHORT,
                        ).show()
                }
            }
        }
    }

    when (val state = uiState) {
        is ClientInfoUiState.Success -> {
            Scaffold(
                topBar = {
                    ClientInfoTopAppBar(
                        state.client,
                        { viewModel.navigateTo(AppScreen.AppointmentCreate(state.client.id)) },
                        { viewModel.navigateTo(AppScreen.ClientChange(state.client.id)) }
                    )
                }
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 7.dp, vertical = 16.dp),
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(7.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(Modifier.padding(horizontal = 10.dp, vertical = 7.dp)) {
                            Text(
                                text = stringResource(R.string.about),
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                fontSize = 17.sp,
                            )
                            Text(
                                modifier = Modifier.padding(bottom = 5.dp),
                                text = state.client.about,
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 17.sp
                            )
                        }

                    }
                    LazyColumn() {

                        item {
                            Text(
                                modifier = Modifier
                                    .alpha(0.9f)
                                    .fillMaxWidth()
                                    .padding(7.dp),
                                text = "Ближайшие Записи:",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 19.sp,
                            )
                        }

                        items(state.client.currentList) { item ->
                            AppointmentListElement(
                                if(item.cancelled) MaterialTheme.colorScheme.scrim.copy(alpha = 0.3f) else MaterialTheme.colorScheme.secondaryContainer,
                                item,
                                { viewModel.navigateTo(AppScreen.AppointmentInfo(item.id)) },
                                text1 = item.services,
                                text2 = "${item.day} ${stringResource(item.month.toMonthNameRes())} ${item.year}"
                            )
                        }

                        item {
                            Text(
                                modifier = Modifier
                                    .alpha(0.9f)
                                    .fillMaxWidth()
                                    .padding(7.dp),
                                text = stringResource(R.string.lastAppointments),
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 19.sp,
                            )
                        }
                        items(state.client.pastList) { item ->
                            AppointmentListElement(
                                if(item.cancelled) MaterialTheme.colorScheme.scrim.copy(alpha = 0.3f) else MaterialTheme.colorScheme.primaryContainer,
                                appointmentInfo = item,
                                onClick = { viewModel.navigateTo(AppScreen.AppointmentInfo(item.id)) },
                                text1 = item.services,
                                text2 = "${item.day} ${stringResource(item.month.toMonthNameRes())} ${item.year}"
                            )
                        }
                    }
                }


            }

        }

        else -> {
            CircularProgressIndicator(color = primaryLight)
        }
    }

}
