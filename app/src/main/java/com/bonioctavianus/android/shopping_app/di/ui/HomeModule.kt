package com.bonioctavianus.android.shopping_app.di.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.bonioctavianus.android.shopping_app.ui.home.HomeFragment
import com.bonioctavianus.android.shopping_app.ui.home.HomeViewModel
import com.bonioctavianus.android.shopping_app.ui.home.HomeViewModelFactory
import dagger.Module
import dagger.Provides

@Module
object HomeModule {

    @Provides
    fun provideViewModelStoreOwner(fragment: HomeFragment): ViewModelStoreOwner {
        return fragment
    }

    @Provides
    fun provideHomeViewModel(
        owner: ViewModelStoreOwner,
        factory: HomeViewModelFactory
    ): HomeViewModel {
        return ViewModelProvider(owner, factory).get(HomeViewModel::class.java)
    }
}