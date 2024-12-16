package com.lab6.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.lab6.data.entity.WeatherMain
import com.lab6.data.entity.response.WeatherDescription

@Composable
fun WeatherMainCustomView(weatherMain: WeatherMain, weatherDescription: WeatherDescription, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.elevatedCardElevation(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF80D0FF), Color(0xFF3D94FF))
                    )
                )
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberImagePainter("http://openweathermap.org/img/wn/${weatherDescription.icon}@2x.png"),
                    contentDescription = "Weather Icon",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(end = 20.dp)
                )

                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "Temp: ${weatherMain.temp}°C",
                        fontSize = 28.sp,
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        "Feels Like: ${weatherMain.feels_like}°C",
                        fontSize = 18.sp,
                        color = Color(0xCCFFFFFF),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        "Humidity: ${weatherMain.humidity}%",
                        fontSize = 18.sp,
                        color = Color(0xB3FFFFFF),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        "Pressure: ${weatherMain.pressure} hPa",
                        fontSize = 18.sp,
                        color = Color(0xB3FFFFFF),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}