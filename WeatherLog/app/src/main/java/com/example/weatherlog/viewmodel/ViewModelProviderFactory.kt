package com.example.weatherlog.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class ViewModelProviderFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}