package com.hfad.mycosmetologist.presentation.main.clients.clientInfo.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfad.mycosmetologist.R
import com.hfad.mycosmetologist.presentation.main.clients.clientInfo.entity.ClientInfo
import com.hfad.mycosmetologist.presentation.util.uiComponents.TopAppBar


@Composable
fun ClientInfoTopAppBar(
    client: ClientInfo,
    onClickCreateAppointmentButton: () -> Unit,
    onClickChangeButton: () -> Unit
) {
    TopAppBar(
        headlineText = client.name
    ) {
        Text(
            text = client.phone,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onClickChangeButton, modifier = Modifier
                    .padding(3.dp)
                    .weight(1.2f)
                    .fillMaxWidth(), shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    contentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_edit_24),
                        contentDescription = "Edit"
                    )
                    Text(
                        text = "Редактировать",
                        style = MaterialTheme.typography.headlineMedium,
                        //color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Button(
                onClick = onClickCreateAppointmentButton, modifier = Modifier
                    .padding(3.dp)
                    .weight(1f)
                    .fillMaxWidth(), shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.6f)
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_calendar_add_on_24),
                        contentDescription = "Create appointment"
                    )
                    Text(
                        text = "Записать",
                        style = MaterialTheme.typography.headlineMedium,
                        //  color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

//@Composable
//fun ClientInfoTopAppBar(
//    client: ClientInfo,
//    onClickCreateAppointmentButton: () -> Unit,
//    onClickChangeButton: () -> Unit
//) {
//    Surface(
//        modifier = Modifier
//            .fillMaxWidth(),
//        shape = RoundedCornerShape(
//            bottomStart = 28.dp,
//            bottomEnd = 28.dp
//        ),
//        tonalElevation = 8.dp,
//    ) {
//        Box(
//            modifier =
//                Modifier
//                    .fillMaxWidth()
//                    .background(
//                        Brush.horizontalGradient(
//                            listOf(
//                                MaterialTheme.colorScheme.primaryContainer,
//                                MaterialTheme.colorScheme.secondaryContainer,
//                            ),
//                        ),
//                    ),
//        ) {
//            Column(
//                modifier = Modifier
//                    .padding(start = 15.dp, end = 15.dp, top = 30.dp, bottom = 15.dp)
//                    .fillMaxWidth(), horizontalAlignment = Alignment.Start
//            ) {
//                Text(
//                    text = client.name,
//                    style = MaterialTheme.typography.headlineLarge,
//                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.9f),
//                    fontSize = 40.sp,
//                    fontWeight = FontWeight.ExtraBold
//                )
//                Text(
//                    text = client.phone,
//                    style = MaterialTheme.typography.headlineMedium,
//                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
//                    fontSize = 17.sp,
//                    fontWeight = FontWeight.Bold
//                )
//                Row(
//                    Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Button(
//                        onClick = onClickChangeButton, modifier = Modifier
//                            .padding(3.dp)
//                            .weight(1.2f)
//                            .fillMaxWidth(), shape = RoundedCornerShape(14.dp),
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = MaterialTheme.colorScheme.surfaceContainer,
//                            contentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
//                        ),
//                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
//                    ) {
//                        Row(
//                            horizontalArrangement = Arrangement.Center,
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Icon(
//                                painter = painterResource(id = R.drawable.outline_edit_24),
//                                contentDescription = "Edit"
//                            )
//                            Text(
//                                text = "Редактировать",
//                                style = MaterialTheme.typography.headlineMedium,
//                                //color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
//                                fontSize = 16.sp,
//                                fontWeight = FontWeight.Bold
//                            )
//                        }
//                    }
//
//                    Button(
//                        onClick = onClickCreateAppointmentButton, modifier = Modifier
//                            .padding(3.dp)
//                            .weight(1f)
//                            .fillMaxWidth(), shape = RoundedCornerShape(14.dp),
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
//                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.6f)
//                        ),
//                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
//                    ) {
//                        Row(
//                            horizontalArrangement = Arrangement.Center,
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Icon(
//                                painter = painterResource(id = R.drawable.outline_calendar_add_on_24),
//                                contentDescription = "Create appointment"
//                            )
//                            Text(
//                                text = "Записать",
//                                style = MaterialTheme.typography.headlineMedium,
//                                //  color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
//                                fontSize = 16.sp,
//                                fontWeight = FontWeight.Bold
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
