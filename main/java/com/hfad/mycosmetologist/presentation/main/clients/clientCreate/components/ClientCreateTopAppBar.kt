package com.hfad.mycosmetologist.presentation.main.clients.clientCreate.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ClientCreateTopAppBar() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(0.7f),
        shape = RoundedCornerShape(
            bottomStart = 28.dp,
            bottomEnd = 28.dp
        ),
        tonalElevation = 8.dp,
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
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp, top = 30.dp, bottom = 15.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = "Регистрация Клиента",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = 40.sp,
                )
            }
        }
    }
}
