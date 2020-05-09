package com.bonioctavianus.android.shopping_app.ui.list.item.v2

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bonioctavianus.android.shopping_app.R
import com.bonioctavianus.android.shopping_app.model.Item
import com.bonioctavianus.android.shopping_app.ui.widget.VerticalItemDecorator
import io.reactivex.Observable

class ItemListV2(context: Context, attributeSet: AttributeSet) :
    RecyclerView(context, attributeSet) {

    private val mLayoutManager = LinearLayoutManager(context)
    private val mAdapter = ItemAdapterV2()

    init {
        layoutManager = mLayoutManager

        addItemDecoration(
            VerticalItemDecorator(
                resources.getDimension(R.dimen.default_list_item_spacing).toInt()
            )
        )

        adapter = mAdapter
    }

    fun submitItems(items: List<Item>) {
        mAdapter.submitItems(items)
    }

    fun intents(): Observable<ItemAdapterV2.Intent> = mAdapter.intents()
}