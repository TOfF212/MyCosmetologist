package com.hfad.mycosmetologist.presentation.util.uiComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfad.mycosmetologist.R
import com.hfad.mycosmetologist.presentation.util.entity.PresentationAppointment
import com.hfad.mycosmetologist.presentation.util.toMonthNameRes

@Composable
fun AppointmentListElement(
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
    appointmentInfo: PresentationAppointment,
    onClick: () -> Unit,
    text1: String = appointmentInfo.clientName,
    text2: String = appointmentInfo.services,
    showDateInsteadOfTime: Boolean = false,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, bottom = 3.dp, start = 7.dp, end = 7.dp),
        shape = RoundedCornerShape(14.dp),
        onClick = onClick,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    backgroundColor,
                )
                .padding(5.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(modifier = Modifier.weight(5f)) {
                    Text(
                        modifier = Modifier.alpha(0.95f),
                        text = text1,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        modifier = Modifier.alpha(0.65f),
                        text = text2,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(bottom = 5.dp, start = 30.dp)
                        .weight(4f),
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    horizontalAlignment = Alignment.End,
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(14.dp),
                            )
                            .fillMaxWidth(),
                    ) {
                        Row {
                            Icon(
                                painter = painterResource(
                                    id = if (showDateInsteadOfTime) {
                                        R.drawable.baseline_calendar_today_24
                                    } else {
                                        R.drawable.baseline_access_time_24
                                    }
                                ),
                                contentDescription = "time",
                            )
                            Text(
                                modifier = Modifier.alpha(0.92f),
                                text = if (showDateInsteadOfTime) {
                                    "${appointmentInfo.day} ${
                                        stringResource(appointmentInfo.month.toMonthNameRes())
                                    }"
                                } else {
                                    "${appointmentInfo.startTime}-${appointmentInfo.endTime}"
                                },
                                maxLines = 1,
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(14.dp),
                            )
                            .fillMaxWidth(),
                    ) {
                        Row {
                            Icon(
                                painter = painterResource(id = R.drawable.outline_attach_money_24),
                                contentDescription = "time",
                            )
                            Text(
                                modifier = Modifier.alpha(0.92f),
                                text = appointmentInfo.profit,
                                maxLines = 1,
                            )
                        }
                    }
                }
            }
        }
    }
}
