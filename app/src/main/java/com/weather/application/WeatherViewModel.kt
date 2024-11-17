package com.weather.application

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {
    val weatherData = MutableLiveData<WeatherResponse?>()
    val error = MutableLiveData<String>()

    fun fetchWeather(city: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val coordinates = repository.getCoordinates(city, apiKey)
                if (coordinates != null) {
                    val weather = repository.getWeatherByCoordinates(coordinates.lat, coordinates.lon, apiKey)
                    weatherData.value = weather
                } else {
                    error.value = "Failed to retrieve coordinates for the city"
                }
            } catch (e: Exception) {
                error.value = "An error occurred: ${e.message}"
            }
        }
    }
}