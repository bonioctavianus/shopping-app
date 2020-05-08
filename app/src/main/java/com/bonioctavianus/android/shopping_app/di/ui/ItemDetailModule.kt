package com.bonioctavianus.android.shopping_app.di.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.bonioctavianus.android.shopping_app.ui.detail.ItemDetailFragment
import com.bonioctavianus.android.shopping_app.ui.detail.ItemDetailViewModel
import com.bonioctavianus.android.shopping_app.ui.detail.ItemDetailViewModelFactory
import dagger.Module
import dagger.Provides

@Module
object ItemDetailModule {

    @Provides
    fun provideViewModelStoreOwner(fragment: ItemDetailFragment): ViewModelStoreOwner {
        return fragment
    }

    @Provides
    fun provideItemDetailViewModel(
        owner: ViewModelStoreOwner,
        factory: ItemDetailViewModelFactory
    ): ItemDetailViewModel {
        return ViewModelProvider(owner, factory).get(ItemDetailViewModel::class.java)
    }
}