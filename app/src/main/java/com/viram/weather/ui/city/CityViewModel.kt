package com.viram.weather.ui.city

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.viram.weather.api.WeatherRepository
import javax.inject.Inject

class CityViewModel @Inject constructor(val repository: WeatherRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
}