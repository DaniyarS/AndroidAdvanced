package com.example.weatherlog.api

import com.example.weatherlog.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/weather?")
    suspend fun getMain(
        @Query("q") cityName: String?,
        @Query("APPID") appId: String
    ) : Response<WeatherResponse>
}
