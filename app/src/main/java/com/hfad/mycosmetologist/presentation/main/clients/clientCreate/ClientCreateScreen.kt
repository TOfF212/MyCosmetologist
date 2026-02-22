package com.hfad.mycosmetologist.presentation.main.clients.clientCreate

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hfad.mycosmetologist.R
import com.hfad.mycosmetologist.domain.exceptions.ObjectIsAlreadyExistException
import com.hfad.mycosmetologist.presentation.navigation.AppScreen
import com.hfad.mycosmetologist.presentation.navigation.Navigator
import com.hfad.mycosmetologist.presentation.util.uiComponents.TopAppBar

@Composable
fun ClientCreateScreen(
    navigator: Navigator,
    viewModel: ClientCreateViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val phone by viewModel.phone.collectAsState()
    val name by viewModel.name.collectAsState()
    val about by viewModel.about.collectAsState()
    val errorMsg = stringResource(R.string.auth_error)

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is CreateClientEvent.ShowError ->
                    if (event.e is ObjectIsAlreadyExistException) {
                        Toast
                            .makeText(
                                context,
                                context.getString(R.string.ClientIsAlreadyExist),
                                Toast.LENGTH_SHORT,
                            ).show()
                    } else {
                        Toast
                            .makeText(
                                context,
                                errorMsg,
                                Toast.LENGTH_SHORT,
                            ).show()
                    }


                CreateClientEvent.Navigate ->
                    navigator.goTo(AppScreen.ClientsList)
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar("Регистрация Клиента", {})
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

                OutlinedTextField(
                    value = name,
                    onValueChange = viewModel::onNameChanged,
                    label = { Text(stringResource(R.string.auth_name)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                        cursorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                        focusedLabelColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = viewModel::onPhoneChanged,
                    label = { Text(stringResource(R.string.auth_phone)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                        cursorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                        focusedLabelColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                    ),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),

                    )

                OutlinedTextField(
                    value = about,
                    onValueChange = viewModel::onAboutChanged,
                    label = { Text(stringResource(R.string.AboutClient)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                        unfocusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                        cursorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                        focusedLabelColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    maxLines = 4
                )
                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = viewModel::onSubmitClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 15.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                ) {
                    Text(stringResource(R.string.auth_register), fontWeight = FontWeight.Bold)
                }
            }


        }
    }
}
