package com.viram.weather

import android.app.Activity
import android.app.Application
import com.viram.weather.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class MainApp : Application(),HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> {
       return dispatchingAndroidInjector
    }

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
    }
}