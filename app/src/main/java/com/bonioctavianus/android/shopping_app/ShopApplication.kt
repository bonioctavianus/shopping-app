package com.bonioctavianus.android.shopping_app

import android.app.Application
import timber.log.Timber

class ShopApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}