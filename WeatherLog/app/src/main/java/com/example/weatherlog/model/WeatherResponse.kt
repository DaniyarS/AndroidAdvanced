package com.example.weatherlog.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse (
    @SerializedName("weather")
    val weather: ArrayList<WeatherData>? = null,
    @SerializedName("main")
    val main: Main? = null,
    @SerializedName("name")
    val name: String? = null
)

data class WeatherData (
    @SerializedName("main")
    val main: String? = null
)

data class Main (
    @SerializedName("temp")
    val temp: Float? = null,
    @SerializedName("feels_like")
    val feelsLike: Float? = null,
    @SerializedName("pressure")
    val pressure: Float? = null
)