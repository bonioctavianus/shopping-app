package com.bonioctavianus.android.shopping_app.ui.search

import com.bonioctavianus.android.shopping_app.model.Item
import com.bonioctavianus.android.shopping_app.usecase.Result
import com.bonioctavianus.android.shopping_app.usecase.SearchItem
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class SearchInteractor @Inject constructor(
    private val mSearchItem: SearchItem
) {
    fun compose(): ObservableTransformer<SearchIntent, SearchViewState> {
        return ObservableTransformer { intents ->
            intents.publish { shared ->
                Observable.mergeArray(
                    shared.ofType(SearchIntent.SearchItem::class.java)
                        .compose(searchItem),
                    shared.ofType(SearchIntent.SelectItem::class.java)
                        .compose(selectItem)
                ).mergeWith(
                    shared.filter { intent ->
                        intent !is SearchIntent.SearchItem
                                && intent !is SearchIntent.SelectItem
                    }.flatMap { intent ->
                        Observable.error<SearchViewState>(
                            IllegalArgumentException("Unknown intent type: $intent")
                        )
                    }
                )
            }
        }
    }

    private val searchItem =
        ObservableTransformer<SearchIntent.SearchItem, SearchViewState> { intents ->
            intents.flatMap { intent ->
                mSearchItem.search(intent.title)
                    .map { result ->
                        when (result) {
                            is Result.InFlight -> {
                                SearchViewState.SearchItem.InFlight
                            }
                            is Result.Success<*> -> {
                                val items = result.item as List<Item>

                                if (items.isEmpty()) {
                                    SearchViewState.SearchItem.Empty
                                } else {
                                    SearchViewState.SearchItem.Loaded(items)
                                }
                            }
                            is Result.Error -> {
                                SearchViewState.SearchItem.Error(result.throwable)
                            }
                        }
                    }
            }
        }

    private val selectItem =
        ObservableTransformer<SearchIntent.SelectItem, SearchViewState> { intents ->
            intents.flatMap { intent ->
                Observable.just(
                    SearchViewState.ItemSelected(intent.item)
                )
            }
        }
}