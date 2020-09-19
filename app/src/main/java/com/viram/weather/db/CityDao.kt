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

    @Query("SELECT * FROM UserCity WHERE city = :city")
    fun findByLogin(city: String): LiveData<UserCity>
}