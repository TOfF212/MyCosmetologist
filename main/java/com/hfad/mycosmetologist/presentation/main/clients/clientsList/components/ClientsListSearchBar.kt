package com.hfad.mycosmetologist.presentation.main.clients.clientsList.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.hfad.mycosmetologist.R
import com.hfad.mycosmetologist.presentation.main.clients.clientsList.entity.Client

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientsListSearchBar(
    textFieldState: TextFieldState,
    clients: List<Client>,
    onClick: (String) -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    val query = textFieldState.text.toString()

    val filteredClients = remember(query, clients) {
        if (query.isBlank()) {
            emptyList()
        } else {
            clients.filter { client ->
                client.name.contains(query, ignoreCase = true) ||
                    client.phone.contains(query)
            }
        }
    }

    SearchBar(
        modifier = Modifier.fillMaxWidth(),
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = {
                    textFieldState.edit { replace(0, length, it) }
                    expanded = true
                },
                onSearch = { expanded = false },
                expanded = expanded,
                onExpandedChange = { expanded = it },
                placeholder = { Text(stringResource(R.string.searchClient)) }
            )
        },
        expanded = expanded,
        onExpandedChange = { expanded = it }

    ) {
        Column(Modifier.verticalScroll(rememberScrollState())) {
            filteredClients.forEach { result ->
                ListItem(
                    headlineContent = {
                        Text("${result.name} ${result.phone}")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onClick(result.id)
                            expanded = false
                        }
                )
            }
        }
    }
}
