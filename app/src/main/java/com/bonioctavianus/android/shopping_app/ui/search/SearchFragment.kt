package com.bonioctavianus.android.shopping_app.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bonioctavianus.android.shopping_app.R
import com.bonioctavianus.android.shopping_app.ui.Navigator
import com.bonioctavianus.android.shopping_app.ui.base.BaseFragment
import com.bonioctavianus.android.shopping_app.utils.SingleLiveEvent
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_search.*
import timber.log.Timber
import javax.inject.Inject

class SearchFragment : BaseFragment<SearchIntent, SearchViewState>() {

    @Inject
    lateinit var mNavigator: Navigator
    @Inject
    lateinit var mViewModel: SearchViewModel

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEventHandler()

        component_search.mActivity = requireActivity()

        if (!mViewModel.mRunning) {
            component_search.showKeyboard()
        }
    }

    override fun intents(): Observable<SearchIntent> {
        return component_search.intents()
    }

    override fun bindIntent(intent: Observable<SearchIntent>) {
        mViewModel.bindIntent(intent)
    }

    override fun observeState(state: MutableLiveData<SearchViewState>) {
        state.observe(viewLifecycleOwner, Observer { value ->
            Timber.d("Received State: $value")
            component_search.renderState(value)
        })
    }

    override fun observeEvent(event: SingleLiveEvent<SearchViewState>?) {
        event?.observe(viewLifecycleOwner, Observer { value ->
            Timber.d("Received Event: $value")
            component_search.renderEvent(value)
        })
    }

    override fun state(): MutableLiveData<SearchViewState> = mViewModel.state()

    override fun event(): SingleLiveEvent<SearchViewState>? = mViewModel.event()

    private fun initEventHandler() {
        component_search.mItemSelectedHandler = { item ->
            mNavigator.navigateToItemDetailFragment(this, item)
        }

        component_search.mDismissSearchHandler = { findNavController().popBackStack() }
    }
}