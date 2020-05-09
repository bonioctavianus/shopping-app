package com.bonioctavianus.android.shopping_app.utils

import javax.inject.Inject

class TimeHelper @Inject constructor() {

    fun getCurrentTime(): Long {
        return System.currentTimeMillis()
    }
}