package com.viram.weather.ui.city

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.viram.weather.api.ApiStage
import com.viram.weather.api.WeatherRepository
import com.viram.weather.model.WeatherResult
import javax.inject.Inject

class CityViewModel @Inject constructor(val repository: WeatherRepository) : ViewModel() {



    fun getTodayWeather(latitude: Double?, longitude: Double?): MutableLiveData<ApiStage<WeatherResult>> {

        var data =  MutableLiveData<ApiStage<WeatherResult>>()
        data.value = ApiStage.Loading()

        data =  repository.getTodayWeather(latitude,longitude) as MutableLiveData<ApiStage<WeatherResult>>


        return data

    }
}