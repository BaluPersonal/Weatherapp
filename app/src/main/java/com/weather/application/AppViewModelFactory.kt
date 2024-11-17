package com.weather.application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherViewModelFactory(
    private val repository: WeatherRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

object RetrofitInstance {

    private const val BASE_URL = "https://api.openweathermap.org/"

    // Configure OkHttpClient with logging
    private val client by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY) // You can set Level.BASIC, Level.HEADERS, or Level.BODY based on what you need

        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    // Create a Retrofit instance
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client) // Use the client with the logging interceptor
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Lazy initialization of the API service interface
    val apiService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}