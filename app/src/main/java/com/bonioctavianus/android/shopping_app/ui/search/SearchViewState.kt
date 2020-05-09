package com.bonioctavianus.android.shopping_app.ui.search

import com.bonioctavianus.android.shopping_app.model.Item
import com.bonioctavianus.android.shopping_app.ui.base.MviViewState

sealed class SearchViewState : MviViewState {

    sealed class SearchItem : SearchViewState() {
        object InFlight : SearchItem()
        object Empty : SearchItem()
        data class Loaded(val items: List<Item>) : SearchItem()
        data class Error(val throwable: Throwable?) : SearchItem()
    }

    data class ItemSelected(val item: Item) : SearchViewState()
}