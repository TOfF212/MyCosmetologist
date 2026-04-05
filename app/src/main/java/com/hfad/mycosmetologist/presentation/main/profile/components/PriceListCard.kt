package com.hfad.mycosmetologist.presentation.main.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hfad.mycosmetologist.R
import com.hfad.mycosmetologist.domain.entity.Service

@Composable
fun PriceListCard(
    priceList: List<Service>,
    onEditClick: (Service) -> Unit,
    onAddClick: () -> Unit,
) {
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
                OutlinedButton(onClick = onAddClick) {
                    Text(text = "Добавить")
                }
            }

            if (priceList.isEmpty()) {
                Text(
                    text = "Добавьте первую услугу в прайс-лист.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            } else {
                priceList.forEach { service ->
                    ServiceRow(
                        title = service.name,
                        price = "${service.price} ₽",
                        onEditClick = { onEditClick(service) },
                    )
                }
            }
        }
    }
}

@Composable
private fun ServiceRow(
    title: String,
    price: String,
    onEditClick: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f),
            ),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.1f))
                    .padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(text = title, style = MaterialTheme.typography.bodyLarge)
                Text(text = price, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
            }
            Button(onClick = onEditClick) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_edit_24),
                    contentDescription = "edit service",
                )
            }
        }
    }
}
