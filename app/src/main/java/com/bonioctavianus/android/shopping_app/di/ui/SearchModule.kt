package com.bonioctavianus.android.shopping_app.di.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.bonioctavianus.android.shopping_app.ui.search.SearchFragment
import com.bonioctavianus.android.shopping_app.ui.search.SearchViewModel
import com.bonioctavianus.android.shopping_app.ui.search.SearchViewModelFactory
import dagger.Module
import dagger.Provides

@Module
object SearchModule {

    @Provides
    fun provideViewModelStoreOwner(fragment: SearchFragment): ViewModelStoreOwner {
        return fragment
    }

    @Provides
    fun provideSearchViewModel(
        owner: ViewModelStoreOwner,
        factory: SearchViewModelFactory
    ): SearchViewModel {
        return ViewModelProvider(owner, factory).get(SearchViewModel::class.java)
    }
}