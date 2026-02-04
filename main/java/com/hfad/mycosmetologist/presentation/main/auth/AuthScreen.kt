package com.hfad.mycosmetologist.presentation.main.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hfad.mycosmetologist.R
import com.hfad.mycosmetologist.presentation.navigation.AppScreen
import com.hfad.mycosmetologist.presentation.navigation.Navigator

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navigator: Navigator,
) {
    val context = LocalContext.current
    val phone by viewModel.phone.collectAsState()
    val name by viewModel.name.collectAsState()
    val password by viewModel.password.collectAsState()
    val errorMsg = stringResource(R.string.auth_error)

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                AuthEvent.ShowError ->
                    Toast
                        .makeText(
                            context,
                            errorMsg,
                            Toast.LENGTH_SHORT,
                        ).show()

                AuthEvent.NavigateHome ->
                    navigator.replaceRoot(AppScreen.Home)
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                value = name,
                label = { Text(stringResource(R.string.auth_name)) },
                onValueChange = viewModel::onNameChanged,
            )
            OutlinedTextField(
                value = phone,
                label = { Text(stringResource(R.string.auth_phone)) },
                onValueChange = viewModel::onPhoneChanged,
            )
            OutlinedTextField(
                value = password,
                label = { Text(stringResource(R.string.auth_password)) },
                onValueChange = viewModel::onPasswordChanged,
            )
            Button(onClick = viewModel::onSubmitClick, modifier = Modifier.padding(top = 10.dp)) {
                Text(stringResource(R.string.auth_register))
            }
        }
    }
}
