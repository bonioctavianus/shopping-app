package com.bonioctavianus.android.shopping_app.di.store

import android.content.Context
import android.content.SharedPreferences
import com.bonioctavianus.android.shopping_app.ShopApplication
import com.bonioctavianus.android.shopping_app.store.ShopStore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object StoreModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideSharedPreferences(application: ShopApplication): SharedPreferences {
        return application.getSharedPreferences(ShopStore.PREF_NAME, Context.MODE_PRIVATE)
    }
}