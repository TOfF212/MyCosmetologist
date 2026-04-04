package com.hfad.mycosmetologist.presentation.main.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hfad.mycosmetologist.R
import com.hfad.mycosmetologist.presentation.main.profile.components.ProfileTopAppBar
import com.hfad.mycosmetologist.presentation.util.uiComponents.TopAppBar

@Composable
fun ProfileScreen() {

        Scaffold(
            topBar = {
                    ProfileTopAppBar("Ирина", {}, {})
            },
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 12.dp, vertical = 14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                ContactsCard()
                PriceListCard()
            }
        }
}


@Composable
private fun ContactsCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(text = "Контакты", style = MaterialTheme.typography.titleMedium)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Телефон", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f))
                Text(text = "+7 (999) 123-45-67")
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Email", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f))
                Text(text = "maria.petrova@example.com")
            }
        }
    }
}

@Composable
private fun PriceListCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "Прайс-лист", style = MaterialTheme.typography.titleMedium)
                OutlinedButton(onClick = {}) {
                    Text(text = "Изменить")
                }
            }

            ServiceRow(title = "Женская стрижка", price = "2 000 ₽")
            ServiceRow(title = "Окрашивание (тон в тон)", price = "4 500 ₽")
            ServiceRow(title = "Укладка", price = "1 500 ₽")
            ServiceRow(title = "Уход для волос", price = "2 300 ₽")
        }
    }
}

@Composable
private fun ServiceRow(
    title: String,
    price: String,
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.1f))
                .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column() {
                Text(text = title, style = MaterialTheme.typography.bodyLarge)
                Text(text = price, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)

            }
            Button({}) {Icon( painter = painterResource(id = R.drawable.outline_delete_24),
                contentDescription = "delete service",)}
        }
    }

}
