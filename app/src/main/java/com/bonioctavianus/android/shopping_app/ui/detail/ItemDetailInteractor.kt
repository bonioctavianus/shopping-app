package com.bonioctavianus.android.shopping_app.ui.detail

import com.bonioctavianus.android.shopping_app.usecase.PurchaseItem
import com.bonioctavianus.android.shopping_app.usecase.Result
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class ItemDetailInteractor @Inject constructor(
    private val mPurchaseItem: PurchaseItem
) {

    fun compose(): ObservableTransformer<ItemDetailIntent, ItemDetailViewState> {
        return ObservableTransformer { intents ->
            intents.publish { shared ->
                Observable.mergeArray(
                    shared.ofType(ItemDetailIntent.DisplayItem::class.java)
                        .compose(displayItem),
                    shared.ofType(ItemDetailIntent.ShareItem::class.java)
                        .compose(shareItem),
                    shared.ofType(ItemDetailIntent.UpdateFavoriteStatus::class.java)
                        .compose(updateFavoriteStatus),
                    shared.ofType(ItemDetailIntent.PurchaseItem::class.java)
                        .compose(purchaseItem)
                ).mergeWith(
                    shared.filter { intent ->
                        intent !is ItemDetailIntent.DisplayItem
                                && intent !is ItemDetailIntent.ShareItem
                                && intent !is ItemDetailIntent.UpdateFavoriteStatus
                                && intent !is ItemDetailIntent.PurchaseItem
                    }.flatMap { intent ->
                        Observable.error<ItemDetailViewState>(
                            IllegalArgumentException("Unknown intent type: $intent")
                        )
                    }
                )
            }
        }
    }

    private val displayItem =
        ObservableTransformer<ItemDetailIntent.DisplayItem, ItemDetailViewState> { intents ->
            intents.flatMap { intent ->
                Observable.just(
                    ItemDetailViewState.ItemDisplayed(intent.item)
                )
            }
        }

    private val shareItem =
        ObservableTransformer<ItemDetailIntent.ShareItem, ItemDetailViewState> { intents ->
            intents.flatMap { intent ->
                val text = "Let\'s share ${intent.item.title} - ${intent.item.price}"
                Observable.just(
                    ItemDetailViewState.ItemShared(text)
                )
            }
        }

    private val updateFavoriteStatus =
        ObservableTransformer<ItemDetailIntent.UpdateFavoriteStatus, ItemDetailViewState> { intents ->
            intents.flatMap { intent ->
                Observable.just(
                    ItemDetailViewState.ItemDisplayed(
                        intent.item
                            .copy(
                                isFavorite = intent.item.isFavorite.not()
                            )
                    )
                )
            }
        }

    private val purchaseItem =
        ObservableTransformer<ItemDetailIntent.PurchaseItem, ItemDetailViewState> { intents ->
            intents.flatMap { intent ->
                mPurchaseItem.purchase(intent.item)
                    .map { result ->
                        when (result) {
                            is Result.InFlight -> {
                                ItemDetailViewState.PurchaseItem.InFlight
                            }
                            is Result.Success<*> -> {
                                val message = "Purchase Success"
                                ItemDetailViewState.PurchaseItem.Success(message)
                            }
                            is Result.Error -> {
                                ItemDetailViewState.PurchaseItem.Error(result.throwable)
                            }
                        }
                    }
            }
        }
}