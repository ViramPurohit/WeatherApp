package com.viram.weather.di

import com.viram.weather.ui.city.AddCityFragment
import com.viram.weather.ui.city.CityFragment
import com.viram.weather.ui.home.HomeFragment
import com.viram.weather.ui.help.HelpFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeCityFragment(): CityFragment

    @ContributesAndroidInjector
    abstract fun contributeAddCityFragment(): AddCityFragment

    @ContributesAndroidInjector
    abstract fun contributeHelpFragment(): HelpFragment
}