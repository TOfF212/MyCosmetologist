package com.hfad.mycosmetologist.presentation.main.appointment.change.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.hfad.mycosmetologist.domain.entity.Service

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentCreateSearchBar(
    textFieldState: TextFieldState,
    priceList: List<Service>,
    onClick: (Service) -> Unit,
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    val query = textFieldState.text.toString()

    val filteredClients = remember(query, priceList) {
        if (query.isBlank()) {
            emptyList()
        } else {
            priceList.filter { client ->
                client.name.contains(query, ignoreCase = true)
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
                placeholder = { Text("Найти услугу...") }
            )
        },
        expanded = expanded,
        onExpandedChange = { expanded = it },
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f),
            inputFieldColors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface.copy(
                    alpha = 0.3f
                ), unfocusedContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f)
            )
        )

    ) {
        LazyColumn {
            items(items = filteredClients) { result ->
                ListItem(
                    headlineContent = { Text(result.name) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onClick(result)
                            expanded = false
                        }
                )
            }


        }
    }
}
