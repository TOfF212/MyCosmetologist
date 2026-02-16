package com.hfad.mycosmetologist.presentation.main.appointment.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hfad.mycosmetologist.presentation.navigation.Navigator
import com.hfad.mycosmetologist.presentation.util.uiComponents.TopAppBar


@Composable
fun AppointmentCreateScreen(
    navigator: Navigator,
    viewModel: AppointmentCreateViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar("Создание записи", {
                
            })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 16.dp),
        ) {

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
            }
        }
    }
}
