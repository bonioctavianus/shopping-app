package com.bonioctavianus.android.shopping_app.ui.profile

import com.bonioctavianus.android.shopping_app.model.Item
import com.bonioctavianus.android.shopping_app.usecase.DoSignOut
import com.bonioctavianus.android.shopping_app.usecase.GetPurchasedItem
import com.bonioctavianus.android.shopping_app.usecase.Result
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class ProfileInteractor @Inject constructor(
    private val mGetPurchasedItem: GetPurchasedItem,
    private val mDoSignOut: DoSignOut
) {
    fun compose(): ObservableTransformer<ProfileIntent, ProfileViewState> {
        return ObservableTransformer { intents ->
            intents.publish { shared ->
                Observable.mergeArray(
                    shared.ofType(ProfileIntent.GetPurchasedItems::class.java)
                        .compose(getPurchasedItems),
                    shared.ofType(ProfileIntent.SelectItem::class.java)
                        .compose(selectItem),
                    shared.ofType(ProfileIntent.SelectMenuSignOut::class.java)
                        .compose(selectMenuSignOut)
                ).mergeWith(
                    shared.filter { intent ->
                        intent !is ProfileIntent.GetPurchasedItems
                                && intent !is ProfileIntent.SelectItem
                                && intent !is ProfileIntent.SelectMenuSignOut
                    }.flatMap { intent ->
                        Observable.error<ProfileViewState>(
                            IllegalArgumentException("Unknown intent type: $intent")
                        )
                    }
                )
            }
        }
    }

    private val getPurchasedItems =
        ObservableTransformer<ProfileIntent.GetPurchasedItems, ProfileViewState> { intents ->
            intents.flatMap {
                mGetPurchasedItem.getItems()
                    .map { result ->
                        when (result) {
                            is Result.InFlight -> {
                                ProfileViewState.GetPurchasedItems.InFlight
                            }
                            is Result.Success<*> -> {
                                val items = result.item as List<Item>

                                if (items.isEmpty()) {
                                    ProfileViewState.GetPurchasedItems.Empty
                                } else {
                                    ProfileViewState.GetPurchasedItems.Loaded(items)
                                }
                            }
                            is Result.Error -> {
                                ProfileViewState.GetPurchasedItems.Error(result.throwable)
                            }
                        }
                    }
            }
        }

    private val selectItem =
        ObservableTransformer<ProfileIntent.SelectItem, ProfileViewState> { intents ->
            intents.flatMap { intent ->
                Observable.just(
                    ProfileViewState.ItemSelected(intent.item)
                )
            }
        }

    private val selectMenuSignOut =
        ObservableTransformer<ProfileIntent.SelectMenuSignOut, ProfileViewState> { intents ->
            intents.flatMap {
                mDoSignOut.signOut()
                    .map { result ->
                        when (result) {
                            is Result.InFlight -> {
                                ProfileViewState.SignOut.InFlight
                            }
                            is Result.Success<*> -> {
                                ProfileViewState.SignOut.Success
                            }
                            is Result.Error -> {
                                ProfileViewState.SignOut.Error(result.throwable)
                            }
                        }
                    }
            }
        }
}