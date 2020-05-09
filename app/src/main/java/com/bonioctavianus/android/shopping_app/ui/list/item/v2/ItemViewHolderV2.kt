package com.bonioctavianus.android.shopping_app.ui.list.item.v2

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bonioctavianus.android.shopping_app.model.Item
import com.bonioctavianus.android.shopping_app.extensions.loadImage
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import kotlinx.android.synthetic.main.row_item_product_v2.view.*

class ItemViewHolderV2(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(item: Item): Observable<ItemAdapterV2.Intent> {
        with(item) {
            view.image_item.loadImage(imageUrl)
            view.text_item_name.text = title
            view.text_item_price.text = price
        }
        return view.clicks().map { ItemAdapterV2.Intent.SelectItem(item) }
    }
}