package com.bonioctavianus.android.shopping_app.di.repository

import com.bonioctavianus.android.shopping_app.database.ShopDatabase
import com.bonioctavianus.android.shopping_app.repository.purchase.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object PurchaseRepositoryModule {

    @JvmStatic
    @Singleton
    @Provides
    fun providePurchaseDao(database: ShopDatabase): PurchaseDao {
        return database.purchaseDao()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun providePurchaseService(dao: PurchaseDao): PurchaseService {
        return PurchaseServiceV1(dao)
    }

    @JvmStatic
    @Singleton
    @Provides
    fun providePurchaseRepository(
        service: PurchaseService,
        transformer: PurchaseTransformer
    ): PurchaseRepository {
        return PurchaseRepositoryV1(service, transformer)
    }
}