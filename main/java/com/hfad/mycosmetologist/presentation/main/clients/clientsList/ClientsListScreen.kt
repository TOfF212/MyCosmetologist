package com.hfad.mycosmetologist.presentation.main.clients.clientsList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hfad.mycosmetologist.R
import com.hfad.mycosmetologist.presentation.main.clients.clientsList.components.ClientListElement
import com.hfad.mycosmetologist.presentation.main.clients.clientsList.components.ClientsListTopAppBar
import com.hfad.mycosmetologist.presentation.main.clients.clientsList.entity.ClientListUiState
import com.hfad.mycosmetologist.presentation.navigation.AppScreen
import com.hfad.mycosmetologist.presentation.navigation.Navigator
import com.hfad.mycosmetologist.ui.theme.primaryLight

@Composable
fun ClientsList(
    viewModel: ClientListViewModel = hiltViewModel(),
    navigator: Navigator,
) {

    val state by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.navigate.collect { screen ->
            navigator.goTo(screen)
        }
    }

    when (val ui = state) {
        is ClientListUiState.Success -> {
            Scaffold(
                topBar = {
                    ClientsListTopAppBar(
                        ui.clientList,
                        { id -> viewModel.navigateTo(AppScreen.ClientInfo(id)) }
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(onClick = { viewModel.navigateTo(AppScreen.ClientCreate) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_add_24),
                            contentDescription = "Create client"
                        )
                    }
                }
            ) { paddingValues ->
                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = 2.dp, end = 12.dp, start = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Все клиенты", fontWeight = FontWeight.Bold)
                        Text(
                            text = (state as ClientListUiState.Success).clientList.size.toString(),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.alpha(0.5f)
                        )
                    }
                    LazyColumn {
                        items(items = ui.clientList) { item ->
                            ClientListElement(
                                item.name,
                                item.phone,
                                { viewModel.navigateTo(AppScreen.ClientInfo(item.id)) }
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

