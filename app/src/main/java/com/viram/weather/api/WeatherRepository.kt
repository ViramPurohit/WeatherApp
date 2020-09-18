package com.viram.weather.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.viram.weather.BuildConfig
import com.viram.weather.model.ForecastResult
import com.viram.weather.model.WeatherResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val weatherService: WeatherService
) {

    fun getTodayWeather(latitude: Double?, longitude: Double?): LiveData<ApiStage<WeatherResult>> {

        val data = MutableLiveData<ApiStage<WeatherResult>>()

        data.value = ApiStage.Loading()
        latitude?.let {_latitude->
            longitude?.let {_longitude->
                weatherService.getWeather(_latitude, _longitude,BuildConfig.weather_key)
                    .enqueue(object : Callback<WeatherResult> {
                        override fun onResponse(call: Call<WeatherResult>, response: Response<WeatherResult>) {
                            try {
                                data.value = response.body()?.let { ApiStage.Success(it) }
                            } catch (e: Exception) {
                                e.printStackTrace()
                                data.value = ApiStage.Failure(e)
                            }
                        }

                        override fun onFailure(call: Call<WeatherResult>, t: Throwable) {
                            data.value = ApiStage.Failure(t)
                        }
                    })
            }
        }

        return data
    }
    fun getForecast(): LiveData<ApiStage<ForecastResult>> {

        val data = MutableLiveData<ApiStage<ForecastResult>>()

        data.value = ApiStage.Loading()
        weatherService.getForecast(0.0,0.0,BuildConfig.weather_key,"metric")
            .enqueue(object : Callback<ForecastResult> {
                override fun onResponse(call: Call<ForecastResult>, response: Response<ForecastResult>) {
                    try {
                        data.value = response.body()?.let { ApiStage.Success(it) }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        data.value = ApiStage.Failure(e)
                    }
                }

                override fun onFailure(call: Call<ForecastResult>, t: Throwable) {
                    data.value = ApiStage.Failure(t)
                }
            })

        return data
    }
}