package com.hfad.mycosmetologist.presentation.main.profile.profileChange

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hfad.mycosmetologist.presentation.main.profile.profileChange.components.ProfileChangeField
import com.hfad.mycosmetologist.presentation.main.profile.profileChange.components.ProfileChangeTopAppBar
import com.hfad.mycosmetologist.presentation.navigation.Navigator

@Composable
fun ProfileChangeScreen(
    navigator: Navigator,
) {
    var name by remember { mutableStateOf("Анна Иванова") }
    var phone by remember { mutableStateOf("+7 999 123-45-67") }
    var email by remember { mutableStateOf("anna@mycosmetologist.app") }
    var specialization by remember { mutableStateOf("Косметолог-эстетист") }
    var experience by remember { mutableStateOf("Стаж 8 лет") }
    var about by remember {
        mutableStateOf("Работаю с деликатными техниками ухода и индивидуальным подбором процедур.")
    }

    Scaffold(
        topBar = {
            ProfileChangeTopAppBar(onBackClick = navigator::goBack)
        },
    ) { paddingValues ->
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
                value = name,
                onValueChange = { name = it },
            )
            ProfileChangeField(
                label = "Телефон",
                value = phone,
                onValueChange = { phone = it },
            )
            ProfileChangeField(
                label = "Email",
                value = email,
                onValueChange = { email = it },
            )
            ProfileChangeField(
                label = "Специализация",
                value = specialization,
                onValueChange = { specialization = it },
            )
            ProfileChangeField(
                label = "Опыт",
                value = experience,
                onValueChange = { experience = it },
            )
            ProfileChangeField(
                label = "О себе",
                value = about,
                onValueChange = { about = it },
                minLines = 4,
            )

            Button(
                modifier = Modifier.padding(top = 8.dp),
                onClick = {},
            ) {
                Text(
                    text = "Сохранить",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    }
}
