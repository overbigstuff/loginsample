package com.shirokov.loginsample.service

import com.shirokov.loginsample.model.CityWeather
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.openweathermap.org/"

class WeatherService {
    companion object {
        fun getInstance() : WeatherApi {
            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit.create(WeatherApi::class.java)
        }
    }
}
interface WeatherApi {
//    http://api.openweathermap.org/data/2.5/weather?q=Smolensk,RU&APPID=25216933cde928ecdb75984da0064d84&units=metric

    @GET("data/2.5/weather/")
    fun getWeather(@Query("q") cityName: String, @Query("appid") appid: String, @Query("units") units: String = "metric"): Call<CityWeather>
}