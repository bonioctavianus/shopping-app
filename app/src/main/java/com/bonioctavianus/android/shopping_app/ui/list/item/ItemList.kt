package com.bonioctavianus.android.shopping_app.ui.list.item

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bonioctavianus.android.shopping_app.R
import com.bonioctavianus.android.shopping_app.model.Item
import com.bonioctavianus.android.shopping_app.ui.widget.VerticalItemDecorator
import io.reactivex.Observable

class ItemList(context: Context, attributeSet: AttributeSet) : RecyclerView(context, attributeSet) {

    private val mLayoutManager = LinearLayoutManager(context)
    private val mAdapter = ItemAdapter()

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

    fun intents(): Observable<ItemAdapter.Intent> = mAdapter.intents()
}