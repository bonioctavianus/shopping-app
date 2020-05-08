package com.bonioctavianus.android.shopping_app.ui.detail

import com.bonioctavianus.android.shopping_app.model.Item
import com.bonioctavianus.android.shopping_app.ui.base.MviIntent

sealed class ItemDetailIntent : MviIntent {
    data class DisplayItem(val item: Item) : ItemDetailIntent()
    data class ShareItem(val item: Item) : ItemDetailIntent()
    data class UpdateFavoriteStatus(val item: Item) : ItemDetailIntent()
    data class PurchaseItem(val item: Item) : ItemDetailIntent()
}