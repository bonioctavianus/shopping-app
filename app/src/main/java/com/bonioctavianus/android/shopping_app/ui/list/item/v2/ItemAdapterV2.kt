package com.bonioctavianus.android.shopping_app.ui.list.item.v2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bonioctavianus.android.shopping_app.R
import com.bonioctavianus.android.shopping_app.model.Item
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class ItemAdapterV2 : RecyclerView.Adapter<ItemViewHolderV2>() {

    private var mItems: List<Item> = emptyList()
    private val mSubject: PublishSubject<Intent> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolderV2 {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item_product_v2, parent, false)
        return ItemViewHolderV2(view)
    }

    override fun getItemCount(): Int = mItems.size

    override fun onBindViewHolder(holder: ItemViewHolderV2, position: Int) {
        holder.bind(mItems[position]).subscribe(mSubject)
    }

    fun submitItems(items: List<Item>) {
        mItems = items
        notifyDataSetChanged()
    }

    fun intents(): Observable<Intent> = mSubject.hide()

    sealed class Intent {
        data class SelectItem(val item: Item) : Intent()
    }
}