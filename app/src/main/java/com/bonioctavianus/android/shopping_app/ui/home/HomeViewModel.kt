package com.bonioctavianus.android.shopping_app.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bonioctavianus.android.shopping_app.ui.base.BaseViewModel
import com.bonioctavianus.android.shopping_app.utils.SingleLiveEvent
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject

class HomeViewModel(
    private val mInteractor: HomeInteractor
) : BaseViewModel<HomeIntent, HomeViewState>() {

    private val mState: MutableLiveData<HomeViewState> = MutableLiveData()
    private val mEvent: SingleLiveEvent<HomeViewState> = SingleLiveEvent()

    override fun bindIntent(intent: Observable<HomeIntent>) {
        addDisposable(
            intent
                .compose(mInteractor.compose())
                .subscribe(
                    { value ->
                        when (value) {
                            is HomeViewState.MenuSearchSelected -> mEvent.postValue(value)
                            else -> mState.postValue(value)
                        }
                    },
                    { Timber.e(it) }
                )
        )
    }

    override fun state(): MutableLiveData<HomeViewState> = mState

    override fun event(): SingleLiveEvent<HomeViewState>? = mEvent
}

class HomeViewModelFactory @Inject constructor(
    private val mInteractor: HomeInteractor
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(HomeInteractor::class.java)
            .newInstance(mInteractor)
    }
}