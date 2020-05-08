package com.bonioctavianus.android.shopping_app.store

import android.content.SharedPreferences
import com.bonioctavianus.android.shopping_app.BuildConfig
import javax.inject.Inject

class ShopStore @Inject constructor(private val preferences: SharedPreferences) {

    companion object {
        const val PREF_NAME = BuildConfig.APPLICATION_ID.plus(".preferences")
    }

    fun putString(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String): String {
        return preferences.getString(key, defaultValue) ?: defaultValue
    }

    fun putLong(key: String, value: Long) {
        preferences.edit().putLong(key, value).apply()
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return preferences.getLong(key, defaultValue)
    }
}