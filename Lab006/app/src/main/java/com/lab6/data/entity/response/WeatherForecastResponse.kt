package com.lab6.data.entity.response

import com.lab6.data.entity.WeatherMain


data class WeatherForecastResponse(
    val list: List<DailyForecast>
)

data class DailyForecast(
    val dt: Long,  // Timestamp for the forecasted date
    val main: WeatherMain,  // Temperature and other main info
    val weather: List<WeatherDescription>, // Weather description
    val hourly: List<HourlyForecast>?  // Hourly forecast, if present
)

data class HourlyForecast(
    val dt: Long,  // Timestamp for the hourly forecast
    val main: WeatherMain,  // Temperature and other main info
    val weather: List<WeatherDescription>  // Weather description
)