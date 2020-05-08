package com.bonioctavianus.android.shopping_app.di

import com.bonioctavianus.android.shopping_app.ShopApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class
    ]
)
interface ShopComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: ShopApplication): ShopComponent
    }

    fun inject(application: ShopApplication)
}