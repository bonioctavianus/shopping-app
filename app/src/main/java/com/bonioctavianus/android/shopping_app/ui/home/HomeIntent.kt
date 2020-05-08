package com.bonioctavianus.android.shopping_app.ui.home

import com.bonioctavianus.android.shopping_app.model.Item
import com.bonioctavianus.android.shopping_app.ui.base.MviIntent

sealed class HomeIntent : MviIntent {
    object GetItems : HomeIntent()
    object SelectMenuSearch : HomeIntent()
    data class SelectItem(val item: Item) : HomeIntent()
}