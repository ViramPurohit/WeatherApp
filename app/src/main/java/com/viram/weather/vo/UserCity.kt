package com.viram.weather.vo

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["city"])
class UserCity (
    @field:SerializedName("city")
    var city: String,
    @field:SerializedName("address")
    var address: String,
    @field:SerializedName("latitude")
    var latitude: Double,
    @field:SerializedName("longitude")
    var longitude: Double
)