package com.viram.weather.api

import com.viram.weather.model.ForecastResult
import com.viram.weather.model.WeatherResult
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * REST API access points
 */
interface WeatherService {

    @GET("weather")
    fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String,
        @Query("units") units: String
    ): Call<WeatherResult>

    @GET("forecast")
    fun getForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String,
        @Query("units") units: String
    ): Call<ForecastResult>

}