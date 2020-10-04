package com.example.weatherlog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherlog.model.Weather
import kotlinx.android.synthetic.main.activity_weather_details.*

class WeatherDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_details)

        val weatherData = intent.getParcelableExtra<Weather>(WEATHER_KEY)

        this.title = "Weather in ${weatherData?.cityName}"
        tvWeatherIndex.text = weatherData?.weather
        tvTempIndex.text = weatherData?.temp
        tvFeelsLikeIndex.text = weatherData?.feelsLike
        tvPressureIndex.text = weatherData?.pressure
    }
}