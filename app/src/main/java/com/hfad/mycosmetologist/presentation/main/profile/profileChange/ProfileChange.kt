package com.hfad.mycosmetologist.presentation.main.profile.profileChange

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hfad.mycosmetologist.presentation.main.profile.profileChange.components.ProfileChangeField
import com.hfad.mycosmetologist.presentation.main.profile.profileChange.components.ProfileChangeTopAppBar
import com.hfad.mycosmetologist.presentation.main.profile.profileChange.entity.ProfileChangeAction
import com.hfad.mycosmetologist.presentation.main.profile.profileChange.entity.ProfileChangeEvent
import com.hfad.mycosmetologist.presentation.navigation.Navigator

@Composable
fun ProfileChangeScreen(
    navigator: Navigator,
    viewModel: ProfileChangeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is ProfileChangeEvent.Error -> snackbarHostState.showSnackbar(event.message)
                ProfileChangeEvent.NavigateBack -> navigator.goBack()
            }
        }
    }

    Scaffold(
        topBar = {
            ProfileChangeTopAppBar(onBackClick = navigator::goBack)
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { paddingValues ->
        if (uiState.isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 12.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            ProfileChangeField(
                label = "Имя",
                value = uiState.name,
                onValueChange = { viewModel.onAction(ProfileChangeAction.OnNameChanged(it)) },
            )
            ProfileChangeField(
                label = "Телефон",
                value = uiState.phone,
                onValueChange = { viewModel.onAction(ProfileChangeAction.OnPhoneChanged(it)) },
            )
            ProfileChangeField(
                label = "Email",
                value = uiState.email,
                onValueChange = { viewModel.onAction(ProfileChangeAction.OnEmailChanged(it)) },
            )
            ProfileChangeField(
                label = "Специализация",
                value = uiState.specialization,
                onValueChange = { viewModel.onAction(ProfileChangeAction.OnSpecializationChanged(it)) },
            )
            ProfileChangeField(
                label = "Опыт",
                value = uiState.experience,
                onValueChange = { viewModel.onAction(ProfileChangeAction.OnExperienceChanged(it)) },
            )
            ProfileChangeField(
                label = "О себе",
                value = uiState.about,
                onValueChange = { viewModel.onAction(ProfileChangeAction.OnAboutChanged(it)) },
                minLines = 4,
            )

            Button(
                modifier = Modifier.padding(top = 8.dp),
                onClick = { viewModel.onAction(ProfileChangeAction.OnSaveClicked) },
            ) {
                Text(
                    text = "Сохранить",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}
