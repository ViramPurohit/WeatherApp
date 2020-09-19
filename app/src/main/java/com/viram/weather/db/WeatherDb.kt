package com.viram.weather.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.viram.weather.vo.UserCity

@Database(
    entities = [
        UserCity::class,
       ],
    version = 1,
    exportSchema = false
)
abstract class WeatherDb : RoomDatabase() {

    abstract fun userCityDao(): CityDao

}
