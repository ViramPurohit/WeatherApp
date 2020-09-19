package com.viram.weather.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.viram.weather.ui.city.AddCityViewModel
import com.viram.weather.ui.city.CityViewModel
import com.viram.weather.ui.home.HomeViewModel
import com.viram.weather.ui.help.HelpViewModel
import com.viram.weather.viewmodel.WeatherViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CityViewModel::class)
    abstract fun bindCityViewModel(cityViewModel: CityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddCityViewModel::class)
    abstract fun bindAddCityViewModel(addCityViewModel: AddCityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HelpViewModel::class)
    abstract fun bindHelpViewModel(helpViewModel: HelpViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: WeatherViewModelFactory): ViewModelProvider.Factory
}