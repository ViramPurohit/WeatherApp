package com.viram.weather.ui.city

import androidx.lifecycle.ViewModel
import com.viram.weather.api.WeatherRepository
import com.viram.weather.vo.UserCity
import javax.inject.Inject

class AddCityViewModel @Inject constructor(val repository: WeatherRepository) : ViewModel() {

    fun addUserCity(userCity: UserCity) {
        repository.insert(userCity)
    }

}