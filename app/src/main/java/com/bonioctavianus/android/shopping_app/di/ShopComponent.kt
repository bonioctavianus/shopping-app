package com.bonioctavianus.android.shopping_app.di

import com.bonioctavianus.android.shopping_app.ShopApplication
import com.bonioctavianus.android.shopping_app.di.network.NetworkModule
import com.bonioctavianus.android.shopping_app.di.repository.AuthRepositoryModule
import com.bonioctavianus.android.shopping_app.di.repository.ItemRepositoryModule
import com.bonioctavianus.android.shopping_app.di.store.StoreModule
import com.bonioctavianus.android.shopping_app.di.ui.FragmentModule
import com.bonioctavianus.android.shopping_app.di.user.UserModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        StoreModule::class,
        NetworkModule::class,
        AuthRepositoryModule::class,
        ItemRepositoryModule::class,
        UserModule::class,
        FragmentModule::class
    ]
)
interface ShopComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: ShopApplication): ShopComponent
    }

    fun inject(application: ShopApplication)
}