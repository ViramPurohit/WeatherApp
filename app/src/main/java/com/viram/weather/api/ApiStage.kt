package com.viram.weather.api

import java.lang.Exception

sealed class ApiStage<out T> {

    class Loading<out T> : ApiStage<T>()
    data class Success<out T>(val data: T?) : ApiStage<T>()
//    data class WeatherSuccess<out T>(val data: T?) : ApiStage<T>()
    data class Failure<out T>(val throwable: Throwable) : ApiStage<T>()
    data class FailedMessage<out T>(val data: T?) : ApiStage<T>()
}