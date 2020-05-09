package com.bonioctavianus.android.shopping_app.ui.profile

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
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.view.*
import javax.inject.Inject

class ProfileFragment : BaseFragment<ProfileIntent, ProfileViewState>() {

    @Inject
    lateinit var mNavigator: Navigator
    @Inject
    lateinit var mViewModel: ProfileViewModel

    private val mSubject: PublishSubject<ProfileIntent> = PublishSubject.create()

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.toolbar.setTitle(R.string.nav_menu_profile)
        initActionMenu()
        initEventHandler()
    }

    override fun intents(): Observable<ProfileIntent> {
        return Observable.merge(
            Observable.just(
                ProfileIntent.GetPurchasedItems
            ),
            component_profile.intents(),
            mSubject.hide()
        )
    }

    override fun bindIntent(intent: Observable<ProfileIntent>) {
        mViewModel.bindIntent(intent)
    }

    override fun observeState(state: MutableLiveData<ProfileViewState>) {
        state.observe(viewLifecycleOwner, Observer { value ->
            component_profile.renderState(value)
        })
    }

    override fun observeEvent(event: SingleLiveEvent<ProfileViewState>?) {
        event?.observe(viewLifecycleOwner, Observer { value ->
            component_profile.renderEvent(value)
        })
    }

    override fun state(): MutableLiveData<ProfileViewState> = mViewModel.state()

    override fun event(): SingleLiveEvent<ProfileViewState>? = mViewModel.event()

    private fun initActionMenu() {
        toolbar.inflateMenu(R.menu.fragment_profile_menu)
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_menu_sign_out -> {
                    mSubject.onNext(
                        ProfileIntent.SelectMenuSignOut
                    )
                }
            }
            true
        }
    }

    private fun initEventHandler() {
        component_profile.mItemSelectedHandler = { item ->
            mNavigator.navigateToItemDetailFragment(this, item)
        }

        component_profile.mSignOutHandler = {
            mNavigator.navigateToMainActivity(this)
        }
    }
}