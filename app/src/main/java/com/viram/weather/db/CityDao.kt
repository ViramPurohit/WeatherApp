package com.viram.weather.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.viram.weather.vo.UserCity

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userCity: UserCity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCities(cities: List<UserCity>)


    @Query("DELETE FROM  UserCity WHERE city  = :userCity ")
    fun deleteCity(userCity: String)


    @Query("SELECT * FROM UserCity")
    fun getAllSavedCity(): LiveData<List<UserCity>>
}