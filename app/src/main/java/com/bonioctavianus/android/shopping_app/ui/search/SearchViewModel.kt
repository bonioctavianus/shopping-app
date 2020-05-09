package com.bonioctavianus.android.shopping_app.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bonioctavianus.android.shopping_app.ui.base.BaseViewModel
import com.bonioctavianus.android.shopping_app.utils.SingleLiveEvent
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject

class SearchViewModel(
    private val mInteractor: SearchInteractor
) : BaseViewModel<SearchIntent, SearchViewState>() {

    private val mState: MutableLiveData<SearchViewState> = MutableLiveData()
    private val mEvent: SingleLiveEvent<SearchViewState> = SingleLiveEvent()

    var mRunning: Boolean = false
        private set

    override fun bindIntent(intent: Observable<SearchIntent>) {
        addDisposable(
            intent
                .compose(mInteractor.compose())
                .subscribe(
                    { value ->
                        when (value) {
                            is SearchViewState.ItemSelected -> mEvent.postValue(value)
                            else -> mState.postValue(value)
                        }
                        mRunning = true
                    },
                    { Timber.e(it) }
                )
        )
    }

    override fun state(): MutableLiveData<SearchViewState> = mState

    override fun event(): SingleLiveEvent<SearchViewState>? = mEvent
}

class SearchViewModelFactory @Inject constructor(
    private val mInteractor: SearchInteractor
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(SearchInteractor::class.java)
            .newInstance(mInteractor)
    }
}