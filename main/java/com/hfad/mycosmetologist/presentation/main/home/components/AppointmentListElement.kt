package com.hfad.mycosmetologist.presentation.main.home.components

import com.hfad.mycosmetologist.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hfad.mycosmetologist.domain.entity.Appointment

@Preview
@Composable
fun AppointmentListElement(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(0.7f)
            .padding(top = 5.dp, bottom = 3.dp, start = 7.dp, end = 7.dp),
        shape = RoundedCornerShape(28.dp),
        onClick = {  }
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.secondaryContainer.copy(0.8f))
                .padding(5.dp)
        ){
                    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically){
                        Column {
                            Text(
                                modifier = Modifier.alpha(0.95f),
                                text = "Анна Викторовна",
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Text(
                                modifier = Modifier.alpha(0.65f),
                                text = "Услгула Услуга",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Column(modifier = Modifier.padding(bottom = 5.dp, start = 30.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Box(
                                modifier= Modifier
                                    .background(
                                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.2f),
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .fillMaxWidth(),
                            ){
                                Row {
                                    Icon(painter = painterResource(id = R.drawable.baseline_access_time_24), contentDescription = "time")
                                    Text(
                                        modifier = Modifier.alpha(0.92f),
                                        text = "10:30-11:50",
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                }
                            }
                            Box(
                                modifier= Modifier
                                    .background(
                                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.2f),
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .fillMaxWidth(),
                            ){
                                Row {
                                    Icon(painter = painterResource(id = R.drawable.outline_attach_money_24), contentDescription = "time")
                                    Text(
                                        modifier = Modifier.alpha(0.92f),
                                        text = "11930",
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                }

                            }
                        }

                    }
        }
    }
}