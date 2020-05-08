package com.bonioctavianus.android.shopping_app.ui.list.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bonioctavianus.android.shopping_app.R
import com.bonioctavianus.android.shopping_app.model.Item
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class ItemAdapter : RecyclerView.Adapter<ItemViewHolder>() {

    private var mItems: List<Item> = emptyList()
    private val mSubject: PublishSubject<Intent> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item_product, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = mItems.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
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