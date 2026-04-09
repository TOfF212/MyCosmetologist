package com.hfad.mycosmetologist.presentation.main.clients.clientChange

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hfad.mycosmetologist.presentation.main.clients.clientChange.components.ClientChangeForm
import com.hfad.mycosmetologist.presentation.main.clients.clientChange.components.ClientChangeTopAppBar
import com.hfad.mycosmetologist.presentation.main.clients.clientChange.entity.ClientChangeEvent
import com.hfad.mycosmetologist.presentation.navigation.Navigator

@Composable
fun ClientChangeScreen(
    navigator: Navigator,
    viewModel: ClientChangeViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                ClientChangeEvent.NavigateBack -> navigator.goBack()
                is ClientChangeEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold(
        topBar = { ClientChangeTopAppBar() },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 16.dp),
        ) {
            ClientChangeForm(
                isLoading = state.isLoading,
                name = state.name,
                phone = state.phone,
                about = state.about,
                onNameChanged = viewModel::onNameChanged,
                onPhoneChanged = viewModel::onPhoneChanged,
                onAboutChanged = viewModel::onAboutChanged,
                onSubmitClick = viewModel::onSubmitClick,
            )
        }
    }
}
