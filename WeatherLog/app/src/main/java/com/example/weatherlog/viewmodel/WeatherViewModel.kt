package com.example.weatherlog.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.weatherlog.WeatherRepository
import com.example.weatherlog.model.Weather
import com.example.weatherlog.api.WeatherApiService
import kotlinx.coroutines.*
import java.lang.Exception
import java.util.*
import kotlin.coroutines.CoroutineContext

private const val WEATHER_API_KEY = "95f030374fc4e045e0c947058469c373"

class WeatherViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    private val repository = WeatherRepository(application)
    private val allWeather = repository.getAllWeather()
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private fun insertWeather(weather: Weather) {
        repository.insertWeather(weather)
    }

    fun getAllWeather(): LiveData<List<Weather>> {
        return allWeather
    }

    fun getWeather() {
        launch {
            val time = Calendar.getInstance().time
            withContext(Dispatchers.IO) {
                try {
                    val response = WeatherApiService
                        .getWeatherApi()
                        .getMain(
                            "Almaty,KZ",
                            WEATHER_API_KEY
                        )
                    val weather = Weather()
                    if (response.isSuccessful) {

                        val result = response.body()

                        weather.cityName = result?.name
                        weather.weather = result?.weather?.get(0)?.main
                        weather.temp = result?.main?.temp.toString()
                        weather.feelsLike = result?.main?.feelsLike.toString()
                        weather.pressure = result?.main?.pressure.toString()
                        weather.time = time.toString()
                    }
                    insertWeather(weather)
                } catch (e: Exception) {
                    Log.d("error", e.printStackTrace().toString())
                }
            }
        }
    }
}