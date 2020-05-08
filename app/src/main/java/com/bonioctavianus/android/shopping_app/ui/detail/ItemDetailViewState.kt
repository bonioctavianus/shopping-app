package com.bonioctavianus.android.shopping_app.ui.detail

import com.bonioctavianus.android.shopping_app.model.Item
import com.bonioctavianus.android.shopping_app.ui.base.MviViewState

sealed class ItemDetailViewState : MviViewState {

    data class ItemDisplayed(val item: Item) : ItemDetailViewState()
    data class ItemShared(val text: String) : ItemDetailViewState()

    sealed class PurchaseItem : ItemDetailViewState() {
        object InFlight : PurchaseItem()
        data class Success(val message: String) : PurchaseItem()
        data class Error(val throwable: Throwable?) : PurchaseItem()
    }
}