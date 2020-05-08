package com.bonioctavianus.android.shopping_app.di.ui

import com.bonioctavianus.android.shopping_app.ui.auth.AuthFragment
import com.bonioctavianus.android.shopping_app.ui.detail.ItemDetailFragment
import com.bonioctavianus.android.shopping_app.ui.home.HomeFragment
import com.bonioctavianus.android.shopping_app.ui.splash.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector()
    abstract fun contributeSplashFragment(): SplashFragment

    @ContributesAndroidInjector(modules = [AuthModule::class])
    abstract fun contributeAuthFragment(): AuthFragment

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector(modules = [ItemDetailModule::class])
    abstract fun contributeItemDetailFragment(): ItemDetailFragment
}