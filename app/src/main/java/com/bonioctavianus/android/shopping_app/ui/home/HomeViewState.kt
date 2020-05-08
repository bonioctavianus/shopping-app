package com.bonioctavianus.android.shopping_app.ui.home

import com.bonioctavianus.android.shopping_app.model.Category
import com.bonioctavianus.android.shopping_app.model.Home
import com.bonioctavianus.android.shopping_app.model.Item
import com.bonioctavianus.android.shopping_app.ui.base.MviViewState

sealed class HomeViewState : MviViewState {

    sealed class GetHomeItem : HomeViewState() {
        object InFlight : GetHomeItem()
        object Empty : GetHomeItem()
        data class Loaded(val categories: List<Category>, val items: List<Item>) : GetHomeItem()
        data class Error(val throwable: Throwable?) : GetHomeItem()
    }

    object MenuSearchSelected : HomeViewState()
}