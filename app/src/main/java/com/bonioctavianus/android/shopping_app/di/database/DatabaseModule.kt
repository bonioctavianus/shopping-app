package com.bonioctavianus.android.shopping_app.di.database

import com.bonioctavianus.android.shopping_app.ShopApplication
import com.bonioctavianus.android.shopping_app.database.ShopDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideShopDatabase(application: ShopApplication): ShopDatabase {
        return ShopDatabase.getInstance(application)
    }
}