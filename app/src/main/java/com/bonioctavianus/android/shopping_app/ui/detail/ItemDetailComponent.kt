package com.bonioctavianus.android.shopping_app.ui.detail

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.bonioctavianus.android.shopping_app.R
import com.bonioctavianus.android.shopping_app.model.Item
import com.bonioctavianus.android.shopping_app.utils.loadImage
import com.bonioctavianus.android.shopping_app.utils.makeVisible
import io.reactivex.Observable
import kotlinx.android.synthetic.main.component_item_detail.view.*
import kotlinx.android.synthetic.main.toolbar_item_detail.view.*

class ItemDetailComponent(context: Context, attributeSet: AttributeSet) :
    FrameLayout(context, attributeSet) {

    var mShareItemHandler: ((text: String) -> Unit)? = null

    var mItem: Item? = null
        private set

    init {
        View.inflate(context, R.layout.component_item_detail, this)
    }

    fun renderState(state: ItemDetailViewState) {
        when (state) {
            is ItemDetailViewState.ItemDisplayed -> {
                renderItem(state.item)
            }
        }
    }

    private fun renderItem(item: Item) {
        view_content.makeVisible()

        with(item) {
            image_item.loadImage(imageUrl)
            text_title.text = title
            text_description.text = description
            text_item_price.text = price
            image_favorite.setImageResource(
                if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            )
        }

        mItem = item
    }

    fun renderEvent(event: ItemDetailViewState) {
        when (event) {
            is ItemDetailViewState.ItemShared -> {
                mShareItemHandler?.invoke(event.text)
            }
        }
    }

    fun intents(): Observable<ItemDetailIntent> {
        return getFavoriteStatusIntent()
    }

    private fun getFavoriteStatusIntent(): Observable<ItemDetailIntent> {
        return Observable.create { emitter ->
            image_favorite.setOnClickListener {
                mItem?.let {
                    emitter.onNext(
                        ItemDetailIntent.UpdateFavoriteStatus(it)
                    )
                }
            }
        }
    }
}