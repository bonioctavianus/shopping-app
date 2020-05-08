package com.bonioctavianus.android.shopping_app.di.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.bonioctavianus.android.shopping_app.ui.auth.AuthFragment
import com.bonioctavianus.android.shopping_app.ui.auth.AuthViewModel
import com.bonioctavianus.android.shopping_app.ui.auth.AuthViewModelFactory
import dagger.Module
import dagger.Provides

@Module
object AuthModule {

    @Provides
    fun provideViewModelStoreOwner(fragment: AuthFragment): ViewModelStoreOwner {
        return fragment
    }

    @Provides
    fun provideAuthViewModel(
        owner: ViewModelStoreOwner,
        factory: AuthViewModelFactory
    ): AuthViewModel {
        return ViewModelProvider(owner, factory).get(AuthViewModel::class.java)
    }
}