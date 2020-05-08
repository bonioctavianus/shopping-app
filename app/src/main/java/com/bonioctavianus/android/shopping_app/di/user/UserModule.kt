package com.bonioctavianus.android.shopping_app.di.user

import com.bonioctavianus.android.shopping_app.repository.user.UserService
import com.bonioctavianus.android.shopping_app.repository.user.UserServiceV1
import com.bonioctavianus.android.shopping_app.store.ShopStore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object UserModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideUserService(shopStore: ShopStore): UserService {
        return UserServiceV1(shopStore)
    }
}