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
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.toolbar.*
import timber.log.Timber
import javax.inject.Inject

class HomeFragment : BaseFragment<HomeIntent, HomeViewState>() {

    @Inject
    lateinit var mNavigator: Navigator
    @Inject
    lateinit var mViewModel: HomeViewModel

    private val mSubject: PublishSubject<HomeIntent> = PublishSubject.create()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initActionMenu()
        initEventHandler()
    }

    override fun intents(): Observable<HomeIntent> {
        return Observable.merge(
            Observable.just(
                HomeIntent.GetItems
            ),
            component_home.intents(),
            mSubject.hide()
        )
    }

    override fun bindIntent(intent: Observable<HomeIntent>) {
        mViewModel.bindIntent(intent)
    }

    override fun observeState(state: MutableLiveData<HomeViewState>) {
        state.observe(viewLifecycleOwner, Observer { value ->
            Timber.d("Received State: $value")
            component_home.renderState(value)
        })
    }

    override fun observeEvent(event: SingleLiveEvent<HomeViewState>?) {
        event?.observe(viewLifecycleOwner, Observer { value ->
            Timber.d("Received Event: $value")
            component_home.renderEvent(value)
        })
    }

    override fun state(): MutableLiveData<HomeViewState> = mViewModel.state()

    override fun event(): SingleLiveEvent<HomeViewState>? = mViewModel.event()

    private fun initActionMenu() {
        toolbar.inflateMenu(R.menu.fragment_home_menu)
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_menu_search -> {
                    mSubject.onNext(
                        HomeIntent.SelectMenuSearch
                    )
                }
            }
            true
        }
    }

    private fun initEventHandler() {
        component_home.mMenuSearchSelectedHandler = {
            mNavigator.navigateToSearchFragment(this)
        }
    }
}