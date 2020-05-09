package com.bonioctavianus.android.shopping_app.ui.search

import com.bonioctavianus.android.shopping_app.model.Item
import com.bonioctavianus.android.shopping_app.ui.base.MviIntent

sealed class SearchIntent : MviIntent {
    data class SearchItem(val title: String) : SearchIntent()
    data class SelectItem(val item: Item) : SearchIntent()
}