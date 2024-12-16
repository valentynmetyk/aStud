package com.lab6.ui.screens.current

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lab6.ui.components.CityInputDialog
import com.lab6.ui.components.WeatherForecastCard
import com.lab6.ui.components.WeatherMainCustomView
import org.koin.androidx.compose.getViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun WeatherScreen(
    viewModel: WeatherScreenViewModel = getViewModel()
) {
    val weatherResponseState by viewModel.weatherResponseStateFlow.collectAsState()
    val sevenDayForecastState by viewModel.sevenDayForecastStateFlow.collectAsState()
    val cityName by viewModel.cityName.collectAsState()
    val isDialogOpen by viewModel.isDialogOpen.collectAsState()
    val inputCity by viewModel.inputCity.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState() // Get loading state

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF99DBF1))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(56.dp)
                        .padding(16.dp),
                    color = Color.White
                )
            } else {
                weatherResponseState?.let { weatherResponse ->
                    if (weatherResponse.main != null && weatherResponse.weather.isNotEmpty()) {
                        val tempCelsius = (weatherResponse.main.temp - 273.15).roundToInt() // Change temp from fahrenheit to celsius
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.openCityInputDialog() },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Location Icon",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(28.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = cityName,
                                fontSize = 30.sp,
                                color = Color.White
                            )
                        }
                        WeatherMainCustomView(
                            weatherMain = weatherResponse.main.copy(temp = tempCelsius.toDouble(), feels_like = tempCelsius.toDouble()),
                            weatherDescription = weatherResponse.weather.first()
                        )
                    } else {
                        Text("Weather data is unavailable", color = Color.Gray)
                    }
                }
                Text(
                    text = "Forecast",
                    fontSize = 34.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                )
            }
        }

        sevenDayForecastState?.let { forecastResponse ->
            if (forecastResponse.list.isNotEmpty()) {
                // Create a date format
                val dateFormat = SimpleDateFormat("MMMM d", Locale.ENGLISH)
                val groupedForecasts = forecastResponse.list.groupBy { dailyForecast ->
                    dateFormat.format(Date(dailyForecast.dt * 1000))
                }

                groupedForecasts.forEach { (date, forecasts) ->
                    item {
                        Text(
                            text = date,
                            fontSize = 20.sp,
                            color = Color.White,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(forecasts) { dailyForecast ->
                                val hour = SimpleDateFormat("HH:mm", Locale.ENGLISH).format(Date(dailyForecast.dt * 1000))
                                val tempCelsius = (dailyForecast.main.temp - 273.15).roundToInt()
                                val weatherDesc = dailyForecast.weather.firstOrNull()

                                WeatherForecastCard(
                                    hour = hour,
                                    temperature = tempCelsius,
                                    iconCode = weatherDesc?.icon ?: "01d"
                                )
                            }
                        }
                    }
                }
            } else {
                item {
                    Text("No forecast data available", color = Color.Gray)
                }
            }
        }
    }

    if (isDialogOpen) {
        CityInputDialog(
            currentCity = inputCity,
            onCityChanged = { newCity -> viewModel.updateInputCity(newCity) },
            onDismiss = { viewModel.closeCityInputDialog() },
            onSubmit = { viewModel.submitCity() }
        )
    }
}