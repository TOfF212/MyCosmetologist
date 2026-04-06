package com.hfad.mycosmetologist.presentation.main.appointment.create.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfad.mycosmetologist.R
import com.hfad.mycosmetologist.domain.entity.Service
import org.intellij.lang.annotations.JdkConstants

@Composable
fun ServicesListElement(service: Service, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Row(
            modifier =
                Modifier.padding(7.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column() {
                Text(
                    modifier = Modifier.alpha(0.95f),
                    text = service.name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Row() {
                    Text(
                        modifier = Modifier
                            .alpha(0.65f)
                            .padding(end = 7.dp),
                        text = "${service.durationMinutes} мин",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        modifier = Modifier.alpha(0.65f),
                        text = "${service.price} руб",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )

                }
            }

            IconButton(
                onClick = onClick,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f),
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_delete_24),
                    "Delete"
                )
            }
        }
    }
}

//fun ServicesListElement(service: Service, onClick: () -> Unit) {
//    Card(
//        shape = RoundedCornerShape(14.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
//    ) {
//        Row(
//            modifier =
//                Modifier.padding(7.dp)
//        ) {
//            Text(text = service.name)
//            Text(text = service.price.toString())
//            Text(text = service.durationMinutes.toString())
//            Button(onClick = onClick) {
//                Text(text = "Удольть")
//            }
//
//        }
//    }
//}
