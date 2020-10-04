package com.example.weatherlog.di

import com.example.weatherlog.WeatherRepository
import com.example.weatherlog.viewmodel.ViewModelProviderFactory
import com.example.weatherlog.viewmodel.WeatherViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val repositoryModule = module {
    single { WeatherRepository(androidApplication()) }
}

val viewModelProviderFactory = module {
    single { ViewModelProviderFactory(androidApplication(), get()) }
}

val viewModelModule = module {
    single { WeatherViewModel(androidApplication(), get()) }
}

