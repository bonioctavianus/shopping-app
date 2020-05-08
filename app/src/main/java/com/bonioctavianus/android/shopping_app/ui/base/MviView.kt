package com.bonioctavianus.android.shopping_app.ui.base

import androidx.lifecycle.MutableLiveData
import com.bonioctavianus.android.shopping_app.utils.SingleLiveEvent
import io.reactivex.Observable

interface MviView<I : MviIntent, S : MviViewState> {
    fun intents(): Observable<I>
    fun bindIntent(intent: Observable<I>)
    fun observeState(state: MutableLiveData<S>)
    fun observeEvent(event: SingleLiveEvent<S>?)
    fun state(): MutableLiveData<S>
    fun event(): SingleLiveEvent<S>?
}