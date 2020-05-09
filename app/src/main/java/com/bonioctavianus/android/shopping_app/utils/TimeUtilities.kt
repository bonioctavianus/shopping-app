package com.bonioctavianus.android.shopping_app.utils

import javax.inject.Inject

class TimeUtilities @Inject constructor() {

    fun getCurrentTime(): Long {
        return System.currentTimeMillis()
    }
}