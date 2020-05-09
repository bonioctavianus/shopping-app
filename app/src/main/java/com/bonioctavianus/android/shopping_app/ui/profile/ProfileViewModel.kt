package com.bonioctavianus.android.shopping_app.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bonioctavianus.android.shopping_app.ui.base.BaseViewModel
import com.bonioctavianus.android.shopping_app.utils.SingleLiveEvent
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject

class ProfileViewModel(
    private val mInteractor: ProfileInteractor
) : BaseViewModel<ProfileIntent, ProfileViewState>() {

    private val mState: MutableLiveData<ProfileViewState> = MutableLiveData()
    private val mEvent: SingleLiveEvent<ProfileViewState> = SingleLiveEvent()

    override fun bindIntent(intent: Observable<ProfileIntent>) {
        addDisposable(
            intent
                .compose(mInteractor.compose())
                .subscribe(
                    { value ->
                        when (value) {
                            is ProfileViewState.ItemSelected,
                            is ProfileViewState.SignOut.Success,
                            is ProfileViewState.SignOut.Error -> mEvent.postValue(value)
                            else -> mState.postValue(value)
                        }
                    },
                    { Timber.e(it) }
                )
        )
    }

    override fun state(): MutableLiveData<ProfileViewState> = mState

    override fun event(): SingleLiveEvent<ProfileViewState>? = mEvent
}

class ProfileViewModelFactory @Inject constructor(
    private val mInteractor: ProfileInteractor
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ProfileInteractor::class.java)
            .newInstance(mInteractor)
    }
}