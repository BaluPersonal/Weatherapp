package com.weather.application

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService  {
    @GET("data/2.5/weather")
    suspend fun getWeatherByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "imperial"
    ): Response<WeatherResponse>

    @GET("geo/1.0/direct")
    suspend fun getCoordinatesByCityName(
        @Query("q") cityName: String,
        @Query("limit") limit: Int = 1,
        @Query("appid") apiKey: String
    ): Response<List<GeocodeResponse>>

    @GET("geo/1.0/reverse")
    suspend fun getCityNameByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("limit") limit: Int = 1,
        @Query("appid") apiKey: String
    ): Response<List<GeocodeResponse>>
}

data class GeocodeResponse(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String
)