package com.bonioctavianus.android.shopping_app.di.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.bonioctavianus.android.shopping_app.ui.profile.ProfileFragment
import com.bonioctavianus.android.shopping_app.ui.profile.ProfileViewModel
import com.bonioctavianus.android.shopping_app.ui.profile.ProfileViewModelFactory
import dagger.Module
import dagger.Provides

@Module
object ProfileModule {

    @Provides
    fun provideViewModelStoreOwner(fragment: ProfileFragment): ViewModelStoreOwner {
        return fragment
    }

    @Provides
    fun provideProfileViewModel(
        owner: ViewModelStoreOwner,
        factory: ProfileViewModelFactory
    ): ProfileViewModel {
        return ViewModelProvider(owner, factory).get(ProfileViewModel::class.java)
    }
}