package com.bonioctavianus.android.shopping_app.ui.profile

import com.bonioctavianus.android.shopping_app.model.Item
import com.bonioctavianus.android.shopping_app.ui.base.MviIntent

sealed class ProfileIntent : MviIntent {
    object GetPurchasedItems : ProfileIntent()
    data class SelectItem(val item: Item) : ProfileIntent()
}