package com.bonioctavianus.android.shopping_app.ui.list.item.v1

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bonioctavianus.android.shopping_app.R
import com.bonioctavianus.android.shopping_app.model.Item
import com.bonioctavianus.android.shopping_app.utils.loadImage
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import kotlinx.android.synthetic.main.row_item_product.view.*

class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(item: Item): Observable<ItemAdapter.Intent> {
        with(item) {
            view.image_item.loadImage(imageUrl)
            view.text_item_name.text = title
            view.image_favorite.setImageResource(
                if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            )
        }
        return view.clicks().map { ItemAdapter.Intent.SelectItem(item) }
    }
}