package com.lab6.ui.screens.current

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lab6.data.ServerApi
import com.lab6.data.entity.response.WeatherForecastResponse
import com.lab6.data.entity.response.WeatherResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherScreenViewModel(
    private val serverModule: ServerApi
) : ViewModel() {
    private val _weatherResponseStateFlow = MutableStateFlow<WeatherResponse?>(null)
    val weatherResponseStateFlow: StateFlow<WeatherResponse?> get() = _weatherResponseStateFlow

    private val _cityName = MutableStateFlow("Lviv")
    val cityName: StateFlow<String> = _cityName

    private val _sevenDayForecastStateFlow = MutableStateFlow<WeatherForecastResponse?>(null)
    val sevenDayForecastStateFlow: StateFlow<WeatherForecastResponse?> get() = _sevenDayForecastStateFlow

    private val _isDialogOpen = MutableStateFlow(false)
    val isDialogOpen: StateFlow<Boolean> get() = _isDialogOpen

    private val _inputCity = MutableStateFlow(_cityName.value)
    val inputCity: StateFlow<String> get() = _inputCity

    // State for loading date
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    init {
        getWeather()
    }

    fun updateCity(city: String) {
        _cityName.value = city
        _inputCity.value = city // Update input city
        getWeather()
    }

    fun openCityInputDialog() {
        _isDialogOpen.value = true
    }

    fun closeCityInputDialog() {
        _isDialogOpen.value = false
    }

    fun submitCity() {
        updateCity(_inputCity.value)
        closeCityInputDialog()
    }

    fun updateInputCity(city: String) {
        _inputCity.value = city // Allows updating the input city
    }

    private fun getWeather() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val weatherResponse = serverModule.getCurrentWeatherByCity(city = _cityName.value)
                _weatherResponseStateFlow.value = weatherResponse

                val sevenDayForecast = serverModule.getSevenDayForecastByCity(city = _cityName.value)
                _sevenDayForecastStateFlow.value = sevenDayForecast
            } catch (e: Exception) {
                Log.e("WeatherScreenViewModel", "Error fetching weather", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}