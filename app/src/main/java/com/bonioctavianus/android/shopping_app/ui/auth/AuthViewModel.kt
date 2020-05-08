package com.bonioctavianus.android.shopping_app.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bonioctavianus.android.shopping_app.ui.base.BaseViewModel
import com.bonioctavianus.android.shopping_app.utils.SingleLiveEvent
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject

class AuthViewModel(
    private val mInteractor: AuthInteractor
) : BaseViewModel<AuthIntent, AuthViewState>() {

    private val mState: MutableLiveData<AuthViewState> = MutableLiveData()
    private val mEvent: SingleLiveEvent<AuthViewState> = SingleLiveEvent()

    override fun bindIntent(intent: Observable<AuthIntent>) {
        addDisposable(
            intent
                .compose(mInteractor.compose())
                .subscribe(
                    { value ->
                        when (value) {
                            is AuthViewState.FacebookSignIn.Success,
                            is AuthViewState.GoogleSignIn.Success,
                            is AuthViewState.EmailSignIn.Success -> mEvent.postValue(value)
                            else -> mState.postValue(value)
                        }
                    },
                    { Timber.e(it) }
                )
        )
    }

    override fun state(): MutableLiveData<AuthViewState> = mState

    override fun event(): SingleLiveEvent<AuthViewState>? = mEvent
}

class AuthViewModelFactory @Inject constructor(
    private val mInteractor: AuthInteractor
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(AuthInteractor::class.java)
            .newInstance(mInteractor)
    }
}