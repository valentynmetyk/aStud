package com.lab6.data

import com.lab6.data.entity.response.WeatherForecastResponse
import com.lab6.data.entity.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY: String = "4940ecc1b015199cd28e7cb27229f98e" //  https://home.openweathermap.org/api_keys

interface ServerApi {

    @GET("/data/2.5/weather")
    suspend fun getCurrentWeatherByCity(
        @Query("q") city: String,
        @Query("appid") apiId: String = API_KEY,
        ): WeatherResponse

    @GET("/data/2.5/forecast")
    suspend fun getSevenDayForecastByCity(
        @Query("q") city: String,
        @Query("appid") apiKey: String = API_KEY,
        ): WeatherForecastResponse

}
