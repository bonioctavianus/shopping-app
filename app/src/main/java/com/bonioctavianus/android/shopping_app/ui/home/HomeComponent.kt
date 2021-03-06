package com.bonioctavianus.android.shopping_app.ui.home

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.bonioctavianus.android.shopping_app.R
import com.bonioctavianus.android.shopping_app.model.Category
import com.bonioctavianus.android.shopping_app.model.Item
import com.bonioctavianus.android.shopping_app.ui.list.item.v1.ItemAdapter
import com.bonioctavianus.android.shopping_app.extensions.makeGone
import com.bonioctavianus.android.shopping_app.extensions.makeVisible
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import kotlinx.android.synthetic.main.component_home.view.*
import kotlinx.android.synthetic.main.view_empty.view.*
import kotlinx.android.synthetic.main.view_error.view.*

class HomeComponent(context: Context, attributeSet: AttributeSet) :
    FrameLayout(context, attributeSet) {

    var mMenuSearchSelectedHandler: (() -> Unit)? = null
    var mItemSelectedHandler: ((item: Item) -> Unit)? = null

    init {
        View.inflate(context, R.layout.component_home, this)
    }

    fun renderState(state: HomeViewState) {
        when (state) {
            is HomeViewState.GetHomeItem.InFlight -> {
                renderInFlight()
            }
            is HomeViewState.GetHomeItem.Empty -> {
                renderEmpty()
            }
            is HomeViewState.GetHomeItem.Loaded -> {
                renderLoaded(state.categories, state.items)
            }
            is HomeViewState.GetHomeItem.Error -> {
                renderError(state.throwable)
            }
        }
    }

    private fun renderInFlight() {
        view_in_flight.makeVisible()
        view_empty.makeGone()
        view_error.makeGone()
        view_content.makeGone()
    }

    private fun renderEmpty() {
        view_in_flight.makeGone()
        view_empty.makeVisible()
        view_error.makeGone()
        view_content.makeGone()

        text_empty_message.setText(R.string.home_empty_message)
    }

    private fun renderLoaded(categories: List<Category>, items: List<Item>) {
        view_in_flight.makeGone()
        view_empty.makeGone()
        view_error.makeGone()
        view_content.makeVisible()

        list_category.submitItems(categories)
        list_item.submitItems(items)
    }

    private fun renderError(throwable: Throwable?) {
        view_in_flight.makeGone()
        view_empty.makeGone()
        view_error.makeVisible()
        view_content.makeGone()

        view_error.setErrorMessage(throwable?.message)
    }

    fun renderEvent(event: HomeViewState) {
        when (event) {
            is HomeViewState.MenuSearchSelected -> {
                mMenuSearchSelectedHandler?.invoke()
            }
            is HomeViewState.ItemSelected -> {
                mItemSelectedHandler?.invoke(event.item)
            }
        }
    }

    fun intents(): Observable<HomeIntent> {
        return Observable.merge(
            getItemListIntent(),
            getRetryErrorIntent()
        )
    }
    
    private fun getItemListIntent(): Observable<HomeIntent> {
        return list_item.intents()
            .map { intent ->
                when (intent) {
                    is ItemAdapter.Intent.SelectItem -> {
                        HomeIntent.SelectItem(intent.item)
                    }
                }
            }
    }

    private fun getRetryErrorIntent(): Observable<HomeIntent> {
        return button_error_action.clicks()
            .map { HomeIntent.GetItems }
    }
}