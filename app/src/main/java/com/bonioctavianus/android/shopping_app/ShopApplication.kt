package com.bonioctavianus.android.shopping_app

import android.app.Application
import com.bonioctavianus.android.shopping_app.di.DaggerShopComponent
import com.bonioctavianus.android.shopping_app.di.ShopComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

class ShopApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var mAndroidInjector: DispatchingAndroidInjector<Any>

    private lateinit var mShopComponent: ShopComponent

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initDependencyGraph()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initDependencyGraph() {
        mShopComponent = DaggerShopComponent.factory().create(this)
        mShopComponent.inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = mAndroidInjector
}