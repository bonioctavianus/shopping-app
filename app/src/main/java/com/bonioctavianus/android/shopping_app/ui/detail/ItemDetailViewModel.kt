package com.bonioctavianus.android.shopping_app.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bonioctavianus.android.shopping_app.ui.base.BaseViewModel
import com.bonioctavianus.android.shopping_app.utils.SingleLiveEvent
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject

class ItemDetailViewModel(
    private val mInteractor: ItemDetailInteractor
) : BaseViewModel<ItemDetailIntent, ItemDetailViewState>() {

    private val mState: MutableLiveData<ItemDetailViewState> = MutableLiveData()
    private val mEvent: SingleLiveEvent<ItemDetailViewState> = SingleLiveEvent()

    override fun bindIntent(intent: Observable<ItemDetailIntent>) {
        addDisposable(
            intent
                .compose(mInteractor.compose())
                .subscribe(
                    { value ->
                        when (value) {
                            is ItemDetailViewState.ItemShared,
                            is ItemDetailViewState.PurchaseItem -> mEvent.postValue(value)
                            else -> mState.postValue(value)
                        }
                    },
                    { Timber.e(it) }
                )
        )
    }

    override fun state(): MutableLiveData<ItemDetailViewState> = mState

    override fun event(): SingleLiveEvent<ItemDetailViewState>? = mEvent
}

class ItemDetailViewModelFactory @Inject constructor(
    private val mInteractor: ItemDetailInteractor
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ItemDetailInteractor::class.java)
            .newInstance(mInteractor)
    }
}