package com.example.weatherlog

import android.annotation.SuppressLint
import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.weatherlog.database.WeatherDatabase
import com.example.weatherlog.model.Weather

class WeatherRepository(application: Application) {
    private val database = WeatherDatabase.getInstance(application)

    private val weatherDao = database.weatherDao()
    private val allWeather: LiveData<List<Weather>> = weatherDao.getAllWeather()

    fun insertWeather(weather: Weather) {
        InsertWeatherAsyncTask().execute(weather)
    }

    fun getAllWeather(): LiveData<List<Weather>> {
        return allWeather
    }

    @SuppressLint("StaticFieldLeak")
    inner class InsertWeatherAsyncTask: AsyncTask<Weather, Void, Void>() {
        override fun doInBackground(vararg params: Weather?): Void? {
            params[0]?.let { weatherDao.insertWeather(it) }

            return null
        }
    }
}