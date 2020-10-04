package com.example.weatherlog.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.openweathermap.org/"

object WeatherApiService {

    fun getWeatherApi(): WeatherApi {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
        return retrofit.create(WeatherApi::class.java)
    }
}