package com.daniyars.translatorapp.api

import com.daniyars.translatorapp.models.TranslateMessageResponse
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface YandexTranslateApiService {
    companion object {
        fun getYandexApi(): YandexTranslateApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://translate.yandex.net/")
                .build()
            return retrofit.create(YandexTranslateApiService::class.java)
        }
    }

    @GET("api/v1.5/tr.json/translate")
    fun getTranslationResult(
        @Query("key") key: String,
        @Query("text") text: String,
        @Query("lang") lang: String
    ): Single<TranslateMessageResponse>
}