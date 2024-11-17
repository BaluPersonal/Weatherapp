package com.weather.application

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.weather.application.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: WeatherViewModel by viewModels {
        WeatherViewModelFactory(WeatherRepository(RetrofitInstance.apiService))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSearch.setOnClickListener {
            val city = binding.editTextCity.text.toString()
            if (city.isNotEmpty()) {
                viewModel.fetchWeather(city, "7ccd149bde1cb8465bbcf7b19de3cf2e")
            }
        }

        viewModel.weatherData.observe(this) { weather ->
            if (weather != null) {
                binding.textViewCity.text = weather.name
                val data = "Temperature: ${weather.main.temp}°F"
                binding.textViewTemperature.text = data
                binding.textViewDescription.text = weather.weather[0].description
                // Load icon (using Glide or similar)
                val iconUrl = "https://openweathermap.org/img/w/${weather.weather[0].icon}.png"
                Glide.with(this).load(iconUrl).into(binding.imageViewIcon)
            }
        }

        viewModel.error.observe(this) { error ->
            binding.textViewError.text = error
        }
    }
}