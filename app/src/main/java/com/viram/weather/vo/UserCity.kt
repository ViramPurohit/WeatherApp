package com.viram.weather.vo

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["city"])
class UserCity (
    @field:SerializedName("city")
    val city: String,
    @field:SerializedName("address")
    val address: String,
    @field:SerializedName("latitude")
    val latitude: Double,
    @field:SerializedName("longitude")
    val longitude: Double
)