package com.bonioctavianus.android.shopping_app.ui.home

import com.bonioctavianus.android.shopping_app.model.Home
import com.bonioctavianus.android.shopping_app.usecase.GetHomeItems
import com.bonioctavianus.android.shopping_app.usecase.Result
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class HomeInteractor @Inject constructor(
    private val mGetHomeItems: GetHomeItems
) {
    fun compose(): ObservableTransformer<HomeIntent, HomeViewState> {
        return ObservableTransformer { intents ->
            intents.publish { shared ->
                Observable.mergeArray(
                    shared.ofType(HomeIntent.GetItems::class.java)
                        .compose(getHomeItems),
                    shared.ofType(HomeIntent.SelectMenuSearch::class.java)
                        .compose(selectMenuSearch),
                    shared.ofType(HomeIntent.SelectItem::class.java)
                        .compose(selectItem)
                ).mergeWith(
                    shared.filter { intent ->
                        intent !is HomeIntent.GetItems
                                && intent !is HomeIntent.SelectMenuSearch
                                && intent !is HomeIntent.SelectItem
                    }.flatMap { intent ->
                        Observable.error<HomeViewState>(
                            IllegalArgumentException("Unknown intent type: $intent")
                        )
                    }
                )
            }
        }
    }

    private val getHomeItems =
        ObservableTransformer<HomeIntent.GetItems, HomeViewState> { intents ->
            intents.flatMap {
                mGetHomeItems.getItems()
                    .map { result ->
                        when (result) {
                            is Result.InFlight -> {
                                HomeViewState.GetHomeItem.InFlight
                            }
                            is Result.Success<*> -> {
                                val item = result.item as Home
                                val categories = item.categories
                                val items = item.items

                                if (categories.isEmpty() && items.isEmpty()) {
                                    HomeViewState.GetHomeItem.Empty
                                } else {
                                    HomeViewState.GetHomeItem.Loaded(categories, items)
                                }
                            }
                            is Result.Error -> {
                                HomeViewState.GetHomeItem.Error(result.throwable)
                            }
                        }
                    }
            }
        }

    private val selectMenuSearch =
        ObservableTransformer<HomeIntent.SelectMenuSearch, HomeViewState> { intents ->
            intents.flatMap {
                Observable.just(
                    HomeViewState.MenuSearchSelected
                )
            }
        }

    private val selectItem =
        ObservableTransformer<HomeIntent.SelectItem, HomeViewState> { intents ->
            intents.flatMap { intent ->
                Observable.just(
                    HomeViewState.ItemSelected(intent.item)
                )
            }
        }
}