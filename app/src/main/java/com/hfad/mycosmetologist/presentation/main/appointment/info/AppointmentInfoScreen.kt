package com.hfad.mycosmetologist.presentation.main.appointment.info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfad.mycosmetologist.presentation.main.appointment.info.components.AppointmentInfoSectionCard
import com.hfad.mycosmetologist.presentation.main.appointment.info.components.AppointmentInfoServiceItem
import com.hfad.mycosmetologist.presentation.main.appointment.info.components.AppointmentInfoTopAppBar
import com.hfad.mycosmetologist.presentation.navigation.Navigator

@Composable
fun AppointmentInfoScreen(navigator: Navigator,
                          viewModel: AppointmentInfoViewModel) {
    val services = listOf(
        "Комбинированная чистка" to "3 000 ₽",
        "Уходовая маска" to "1 200 ₽",
    )

    Scaffold(
        topBar = {
            AppointmentInfoTopAppBar(
                onEditClick = {},
                onCancelClick = {},
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 14.dp, vertical = 12.dp),
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                item {
                    AppointmentInfoSectionCard(
                        title = "Клиент",
                        value = "Екатерина Смирнова · +7 999 123-45-67",
                    )
                }
                item {
                    AppointmentInfoSectionCard(
                        title = "Дата и время",
                        value = "12 мая 2026 · 14:00–15:30",
                    )
                }
                item {
                    AppointmentInfoSectionCard(
                        title = "Статус",
                        value = "Подтверждена",
                    )
                }
                item {
                    Text(
                        text = "Услуги",
                        modifier = Modifier
                            .alpha(0.9f)
                            .padding(top = 4.dp),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 19.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                    )
                }
                items(services) { (title, price) ->
                    AppointmentInfoServiceItem(title = title, price = price)
                }
                item {
                    AppointmentInfoSectionCard(
                        title = "Комментарий",
                        value = "Запрос: мягкая чистка и успокаивающий уход без кислот.",
                    )
                }
                item {
                    AppointmentInfoSectionCard(
                        title = "Итог",
                        value = "4 200 ₽",
                    )
                }
            }
        }
    }
}
