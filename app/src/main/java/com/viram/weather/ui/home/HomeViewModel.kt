package com.viram.weather.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.viram.weather.api.ApiStage
import com.viram.weather.api.WeatherRepository
import com.viram.weather.model.WeatherResult
import com.viram.weather.vo.UserCity
import javax.inject.Inject

class HomeViewModel @Inject constructor(val repository: WeatherRepository) : ViewModel() {

    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.

    val allSavedCity: LiveData<List<UserCity>> =  repository.allCity


    fun getTodayWeather(latitude: Double?, longitude: Double?): MutableLiveData<ApiStage<WeatherResult>> {

        var data =  MutableLiveData<ApiStage<WeatherResult>>()
        data.value = ApiStage.Loading()

        data =  repository.getTodayWeather(latitude,longitude) as MutableLiveData<ApiStage<WeatherResult>>


        return data

    }
    fun deleteUserCity(userCity: String) {
        repository.delete(userCity)
    }
}