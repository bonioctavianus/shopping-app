package com.bonioctavianus.android.shopping_app.ui.search

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity
import com.bonioctavianus.android.shopping_app.R
import com.bonioctavianus.android.shopping_app.model.Item
import com.bonioctavianus.android.shopping_app.ui.list.item.v2.ItemAdapterV2
import com.bonioctavianus.android.shopping_app.extensions.hideSoftKeyboard
import com.bonioctavianus.android.shopping_app.extensions.makeGone
import com.bonioctavianus.android.shopping_app.extensions.makeVisible
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.afterTextChangeEvents
import com.mancj.materialsearchbar.MaterialSearchBar
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.component_search.view.*
import kotlinx.android.synthetic.main.toolbar_search.view.*
import kotlinx.android.synthetic.main.view_empty.view.*
import kotlinx.android.synthetic.main.view_error.view.*

class SearchComponent(context: Context, attributeSet: AttributeSet) :
    FrameLayout(context, attributeSet), MaterialSearchBar.OnSearchActionListener {

    var mActivity: FragmentActivity? = null
    var mItemSelectedHandler: ((item: Item) -> Unit)? = null
    var mDismissSearchHandler: (() -> Unit)? = null

    private val mSubject: PublishSubject<SearchIntent> = PublishSubject.create()

    init {
        View.inflate(context, R.layout.component_search, this)

        search_bar.setOnSearchActionListener(this)
    }

    fun showKeyboard() {
        search_bar.performClick()
    }

    fun renderState(state: SearchViewState) {
        when (state) {
            is SearchViewState.SearchItem.Empty -> {
                renderEmpty()
            }
            is SearchViewState.SearchItem.Loaded -> {
                renderLoaded(state.items)
            }
            is SearchViewState.SearchItem.Error -> {
                renderError(state.throwable)
            }
        }
    }

    private fun renderEmpty() {
        view_empty.makeVisible()
        view_error.makeGone()
        list_item.makeGone()

        text_empty_message.setText(R.string.search_empty_message)
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

    fun renderEvent(event: SearchViewState) {
        when (event) {
            is SearchViewState.ItemSelected -> {
                mItemSelectedHandler?.invoke(event.item)
            }
        }
    }

    fun intents(): Observable<SearchIntent> {
        return Observable.merge(
            getListItemIntent(),
            getSearchInputTextIntent(),
            getRetryErrorIntent(),
            mSubject.hide()
        )
    }

    private fun getListItemIntent(): Observable<SearchIntent> {
        return list_item.intents()
            .map { intent ->
                when (intent) {
                    is ItemAdapterV2.Intent.SelectItem -> {
                        SearchIntent.SelectItem(intent.item)
                    }
                }
            }
    }

    private fun getSearchInputTextIntent(): Observable<SearchIntent> {
        return search_bar.searchEditText.afterTextChangeEvents()
            .skipInitialValue()
            .map { it.editable.toString() }
            .filter { it.isNotBlank() }
            .map { SearchIntent.SearchItem(it) }
            .cast(SearchIntent::class.java)
    }

    private fun getRetryErrorIntent(): Observable<SearchIntent> {
        return button_error_action.clicks()
            .map {
                SearchIntent.SearchItem(
                    search_bar.searchEditText.text.toString()
                )
            }
    }

    override fun onButtonClicked(buttonCode: Int) = Unit

    override fun onSearchStateChanged(enabled: Boolean) {
        if (!enabled) {
            mDismissSearchHandler?.invoke()
        }
    }

    override fun onSearchConfirmed(text: CharSequence?) {
        clearSearchFocus()

        mSubject.onNext(
            SearchIntent.SearchItem(text.toString())
        )
    }

    private fun clearSearchFocus() {
        hideSoftKeyboard(
            mActivity
        )
        search_bar.searchEditText.clearFocus()
    }
}