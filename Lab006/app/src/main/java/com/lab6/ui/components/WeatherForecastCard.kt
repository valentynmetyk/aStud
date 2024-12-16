package com.lab6.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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

@Composable
fun WeatherForecastCard(hour: String, temperature: Int, iconCode: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.elevatedCardElevation(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF80D0FF), Color(0xFF3D94FF)),
                        startY = 0f,
                        endY = 1000f
                    )
                )
                .padding(16.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = hour,
                    color = Color.White,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.bodyMedium
                )
                Image(
                    painter = rememberImagePainter("http://openweathermap.org/img/wn/$iconCode@2x.png"),
                    contentDescription = "Weather Icon",
                    modifier = Modifier.size(50.dp)
                )
                Text(
                    text = "$temperature°C",
                    color = Color.White,
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}