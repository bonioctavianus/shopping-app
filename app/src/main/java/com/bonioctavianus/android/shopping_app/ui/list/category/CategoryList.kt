package com.bonioctavianus.android.shopping_app.ui.list.category

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bonioctavianus.android.shopping_app.R
import com.bonioctavianus.android.shopping_app.model.Category
import com.bonioctavianus.android.shopping_app.ui.widget.HorizontalItemDecorator

class CategoryList(context: Context, attributeSet: AttributeSet) :
    RecyclerView(context, attributeSet) {

    private val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    private val mAdapter =
        CategoryAdapter()

    init {
        layoutManager = mLayoutManager

        addItemDecoration(
            HorizontalItemDecorator(
                resources.getDimension(R.dimen.default_list_item_spacing).toInt()
            )
        )

        adapter = mAdapter
    }

    fun submitItems(items: List<Category>) {
        mAdapter.submitItems(items)
    }
}