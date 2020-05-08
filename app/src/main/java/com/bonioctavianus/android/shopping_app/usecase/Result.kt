package com.bonioctavianus.android.shopping_app.usecase

sealed class Result {
    object InFlight : Result()
    data class Success<T>(val item: T) : Result()
    data class Error(val throwable: Throwable?) : Result()
}