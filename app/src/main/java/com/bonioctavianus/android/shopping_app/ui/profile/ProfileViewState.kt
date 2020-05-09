package com.bonioctavianus.android.shopping_app.ui.profile

import com.bonioctavianus.android.shopping_app.model.Item
import com.bonioctavianus.android.shopping_app.ui.base.MviViewState

sealed class ProfileViewState : MviViewState {

    sealed class GetPurchasedItems : ProfileViewState() {
        object InFlight : GetPurchasedItems()
        object Empty : GetPurchasedItems()
        data class Loaded(val items: List<Item>) : GetPurchasedItems()
        data class Error(val throwable: Throwable?) : GetPurchasedItems()
    }

    data class ItemSelected(val item: Item) : ProfileViewState()
}