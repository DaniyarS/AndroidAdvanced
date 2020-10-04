package com.example.weatherlog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.weatherlog.databinding.ActivityWeatherDetailsBinding
import com.example.weatherlog.model.Weather
import kotlinx.android.synthetic.main.activity_weather_details.*

class WeatherDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_details)

        val weatherData = intent.getParcelableExtra<Weather>(WEATHER_KEY)

        this.title = "Weather in ${weatherData?.cityName}"

        val binding: ActivityWeatherDetailsBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_weather_details)

        binding.weatherData = weatherData
    }
}