package com.bonioctavianus.android.shopping_app.ui.list.category

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bonioctavianus.android.shopping_app.model.Category
import com.bonioctavianus.android.shopping_app.extensions.loadImage
import kotlinx.android.synthetic.main.row_item_category.view.*

class CategoryViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(item: Category) {
        with(item) {
            view.image_category.loadImage(imageUrl)
            view.text_category.text = name
        }
    }
}