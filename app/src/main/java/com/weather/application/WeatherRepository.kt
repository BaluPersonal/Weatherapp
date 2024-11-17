package com.weather.application

class WeatherRepository(private val apiService: WeatherApiService) {

    suspend fun getCoordinates(cityName: String, apiKey: String): GeocodeResponse? {
        val response = apiService.getCoordinatesByCityName(cityName, 1, apiKey)
        return if (response.isSuccessful && response.body()?.isNotEmpty() == true) {
            response.body()?.first()
        } else {
            null
        }
    }

    suspend fun getWeatherByCoordinates(lat: Double, lon: Double, apiKey: String): WeatherResponse? {
        val response = apiService.getWeatherByCoordinates(lat, lon, apiKey)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}