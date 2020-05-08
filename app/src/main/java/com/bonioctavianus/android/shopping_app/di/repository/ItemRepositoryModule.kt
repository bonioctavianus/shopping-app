package com.bonioctavianus.android.shopping_app.di.repository

import com.bonioctavianus.android.shopping_app.repository.item.*
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object ItemRepositoryModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideItemApi(retrofit: Retrofit): ItemApi {
        return retrofit.create(ItemApi::class.java)
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideItemService(api: ItemApi): ItemService {
        return ItemServiceV1(api)
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideItemRepository(service: ItemService, transformer: ItemTransformer): ItemRepository {
        return ItemRepositoryV1(service, transformer)
    }
}