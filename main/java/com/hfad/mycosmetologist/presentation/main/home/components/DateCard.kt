package com.hfad.mycosmetologist.presentation.main.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfad.mycosmetologist.R

@Composable
fun DateCard(
    onClick: () -> Unit,
    clients: String,
    date: String,
    revenue: String,
) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(top = 25.dp, bottom = 15.dp, start = 7.dp, end = 7.dp),
        shape = RoundedCornerShape(40.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        onClick = onClick,
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                MaterialTheme.colorScheme.primaryContainer,
                                MaterialTheme.colorScheme.secondaryContainer,
                            ),
                        ),
                    ),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 17.dp, vertical = 5.dp),
            ) {
                Text(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .alpha(0.88f),
                    text = date,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                )
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f),
                                shape = RoundedCornerShape(14.dp),
                            )
                            .padding(30.dp, 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = clients,
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontSize = 40.sp,
                        )
                        Text(
                            text = stringResource(R.string.appointments),
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontSize = 17.sp,
                        )
                    }
                    VerticalDivider(
                        modifier =
                            Modifier
                                .height(90.dp)
                                .width(1.dp),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                    )
                    Column(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = revenue,
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontSize = 40.sp,
                        )
                        Text(
                            text = stringResource(R.string.revenue),
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontSize = 17.sp,
                        )
                    }
                }
            }
        }
    }
}
