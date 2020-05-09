package com.bonioctavianus.android.shopping_app.ui.profile

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.bonioctavianus.android.shopping_app.R
import com.bonioctavianus.android.shopping_app.extensions.makeGone
import com.bonioctavianus.android.shopping_app.extensions.makeVisible
import com.bonioctavianus.android.shopping_app.extensions.showToast
import com.bonioctavianus.android.shopping_app.model.Item
import com.bonioctavianus.android.shopping_app.ui.list.item.v2.ItemAdapterV2
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import kotlinx.android.synthetic.main.component_profile.view.*
import kotlinx.android.synthetic.main.view_empty.view.*
import kotlinx.android.synthetic.main.view_error.view.*

class ProfileComponent(context: Context, attributeSet: AttributeSet) :
    FrameLayout(context, attributeSet) {

    var mItemSelectedHandler: ((item: Item) -> Unit)? = null
    var mSignOutHandler: (() -> Unit)? = null

    init {
        View.inflate(context, R.layout.component_profile, this)
    }

    fun renderState(state: ProfileViewState) {
        when (state) {
            is ProfileViewState.GetPurchasedItems.Empty -> {
                renderEmpty()
            }
            is ProfileViewState.GetPurchasedItems.Loaded -> {
                renderLoaded(state.items)
            }
            is ProfileViewState.GetPurchasedItems.Error -> {
                renderError(state.throwable)
            }
        }
    }

    private fun renderEmpty() {
        view_empty.makeVisible()
        view_error.makeGone()
        list_item.makeGone()

        text_empty_message.setText(R.string.profile_empty_purchase_message)
    }

    private fun renderLoaded(items: List<Item>) {
        view_empty.makeGone()
        view_error.makeGone()
        list_item.makeVisible()

        list_item.submitItems(items)
    }

    private fun renderError(throwable: Throwable?) {
        view_empty.makeGone()
        view_error.makeVisible()
        list_item.makeGone()

        view_error.setErrorMessage(throwable?.message)
    }

    fun renderEvent(event: ProfileViewState) {
        when (event) {
            is ProfileViewState.ItemSelected -> {
                mItemSelectedHandler?.invoke(event.item)
            }
            is ProfileViewState.SignOut.Success -> {
                mSignOutHandler?.invoke()
            }
            is ProfileViewState.SignOut.Error -> {
                showToast(event.throwable?.message)
            }
        }
    }

    fun intents(): Observable<ProfileIntent> {
        return Observable.merge(
            getListItemIntent(),
            getRetryErrorIntent()
        )
    }

    private fun getListItemIntent(): Observable<ProfileIntent> {
        return list_item.intents()
            .map { intent ->
                when (intent) {
                    is ItemAdapterV2.Intent.SelectItem -> {
                        ProfileIntent.SelectItem(intent.item)
                    }
                }
            }
    }

    private fun getRetryErrorIntent(): Observable<ProfileIntent> {
        return button_error_action.clicks()
            .map { ProfileIntent.GetPurchasedItems }
    }
}