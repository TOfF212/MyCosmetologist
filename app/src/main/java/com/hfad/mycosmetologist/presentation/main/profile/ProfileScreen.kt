package com.hfad.mycosmetologist.presentation.main.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hfad.mycosmetologist.domain.entity.Service
import com.hfad.mycosmetologist.presentation.main.profile.components.AddServiceDialog
import com.hfad.mycosmetologist.presentation.main.profile.components.ContactsCard
import com.hfad.mycosmetologist.presentation.main.profile.components.EditServiceDialog
import com.hfad.mycosmetologist.presentation.main.profile.components.PriceListCard
import com.hfad.mycosmetologist.presentation.main.profile.components.ProfileTopAppBar
import com.hfad.mycosmetologist.presentation.main.profile.entity.ProfileEvent
import com.hfad.mycosmetologist.presentation.main.profile.entity.ProfileUiEvent
import com.hfad.mycosmetologist.presentation.main.profile.entity.ProfileUiState
import com.hfad.mycosmetologist.presentation.navigation.AppScreen
import com.hfad.mycosmetologist.presentation.navigation.Navigator

private data class EditDialogState(
    val service: Service,
    val name: String,
    val price: String,
    val duration: String,
)

@Composable
fun ProfileScreen(
    navigator: Navigator,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    var addDialogVisible by remember { mutableStateOf(false) }
    var addName by remember { mutableStateOf("") }
    var addPrice by remember { mutableStateOf("") }
    var addDuration by remember { mutableStateOf("") }
    var editDialogState by remember { mutableStateOf<EditDialogState?>(null) }

    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                ProfileEvent.CloseDialogs -> {
                    addDialogVisible = false
                    addName = ""
                    addPrice = ""
                    addDuration = ""
                    editDialogState = null
                }

                ProfileEvent.OpenAddDialog -> {
                    addDialogVisible = true
                }

                is ProfileEvent.OpenEditDialog -> {
                    editDialogState =
                        EditDialogState(
                            service = event.service,
                            name = event.service.name,
                            price = event.service.price.toString(),
                            duration = event.service.durationMinutes.toString(),
                        )
                }

                is ProfileEvent.Navigate -> {
                    navigator.goTo(event.screen)
                }
            }
        }
    }

    when (val uiState = state) {
        is ProfileUiState.Success -> {
            Scaffold(
                topBar = {
                    ProfileTopAppBar(
                        workerName = uiState.data.name,
                        specialization = uiState.data.specialization,
                        experience = uiState.data.experience,
                        onClickEdit = { viewModel.onEvent(ProfileUiEvent.NavigateTo(AppScreen.Profile)) },
                        onClickStats = { viewModel.onEvent(ProfileUiEvent.NavigateTo(AppScreen.Home)) },
                    )
                },
            ) { paddingValues ->
                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 12.dp, vertical = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    ContactsCard(uiState.data)
                    PriceListCard(
                        priceList = uiState.data.priceList,
                        onEditClick = { service -> viewModel.onEvent(ProfileUiEvent.EditServiceClicked(service)) },
                        onAddClick = { viewModel.onEvent(ProfileUiEvent.AddServiceClicked) },
                    )
                }
            }

            if (addDialogVisible) {
                AddServiceDialog(
                    name = addName,
                    price = addPrice,
                    durationMinutes = addDuration,
                    onNameChange = { addName = it },
                    onPriceChange = { addPrice = it },
                    onDurationChange = { addDuration = it },
                    onCancel = {
                        addDialogVisible = false
                        addName = ""
                        addPrice = ""
                        addDuration = ""
                    },
                    onSave = {
                        viewModel.onEvent(
                            ProfileUiEvent.SaveNewService(
                                name = addName,
                                price = addPrice,
                                durationMinutes = addDuration,
                            ),
                        )
                    },
                )
            }

            editDialogState?.let { dialog ->
                EditServiceDialog(
                    name = dialog.name,
                    price = dialog.price,
                    durationMinutes = dialog.duration,
                    onNameChange = { editDialogState = dialog.copy(name = it) },
                    onPriceChange = { editDialogState = dialog.copy(price = it) },
                    onDurationChange = { editDialogState = dialog.copy(duration = it) },
                    onDismiss = { editDialogState = null },
                    onDelete = { viewModel.onEvent(ProfileUiEvent.DeleteService(dialog.service)) },
                    onSave = {
                        viewModel.onEvent(
                            ProfileUiEvent.SaveUpdatedService(
                                id = dialog.service.id,
                                name = dialog.name,
                                price = dialog.price,
                                durationMinutes = dialog.duration,
                            ),
                        )
                    },
                )
            }
        }

        ProfileUiState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
