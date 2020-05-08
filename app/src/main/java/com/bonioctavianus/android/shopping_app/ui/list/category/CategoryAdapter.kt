package com.bonioctavianus.android.shopping_app.ui.list.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bonioctavianus.android.shopping_app.R
import com.bonioctavianus.android.shopping_app.model.Category

class CategoryAdapter : RecyclerView.Adapter<CategoryViewHolder>() {

    private var mItems: List<Category> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item_category, parent, false)
        return CategoryViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = mItems.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(mItems[position])
    }

    fun submitItems(items: List<Category>) {
        mItems = items
        notifyDataSetChanged()
    }
}