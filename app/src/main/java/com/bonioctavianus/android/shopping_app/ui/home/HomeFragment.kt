package com.bonioctavianus.android.shopping_app.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bonioctavianus.android.shopping_app.R
import com.bonioctavianus.android.shopping_app.ui.Navigator
import com.bonioctavianus.android.shopping_app.ui.base.BaseFragment
import com.bonioctavianus.android.shopping_app.utils.SingleLiveEvent
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject

class HomeFragment : BaseFragment<HomeIntent, HomeViewState>() {

    @Inject
    lateinit var mNavigator: Navigator
    @Inject
    lateinit var mViewModel: HomeViewModel

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun intents(): Observable<HomeIntent> {
        return Observable.just(
            HomeIntent.GetItems
        )
    }

    override fun bindIntent(intent: Observable<HomeIntent>) {
        mViewModel.bindIntent(intent)
    }

    override fun observeState(state: MutableLiveData<HomeViewState>) {
        state.observe(viewLifecycleOwner, Observer { value ->
            Timber.d("Received State: $value")
        })
    }

    override fun observeEvent(event: SingleLiveEvent<HomeViewState>?) {
        event?.observe(viewLifecycleOwner, Observer { value ->
            Timber.d("Received Event: $value")
        })
    }

    override fun state(): MutableLiveData<HomeViewState> = mViewModel.state()

    override fun event(): SingleLiveEvent<HomeViewState>? = mViewModel.event()
}