package com.bonioctavianus.android.shopping_app.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bonioctavianus.android.shopping_app.R
import com.bonioctavianus.android.shopping_app.ui.base.BaseFragment
import com.bonioctavianus.android.shopping_app.utils.SingleLiveEvent
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_item_detail.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class ItemDetailFragment : BaseFragment<ItemDetailIntent, ItemDetailViewState>() {

    private val mArgs: ItemDetailFragmentArgs by navArgs()
    private val mSubject: PublishSubject<ItemDetailIntent> = PublishSubject.create()

    @Inject
    lateinit var mViewModel: ItemDetailViewModel

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_item_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbarNavigation(toolbar)
        initEventHandler()
        initActionMenu()
    }

    override fun intents(): Observable<ItemDetailIntent> {
        return Observable.merge(
            Observable.just(
                ItemDetailIntent.DisplayItem(mArgs.item)
            ),
            component_item_detail.intents(),
            mSubject.hide()
        )
    }

    override fun bindIntent(intent: Observable<ItemDetailIntent>) {
        mViewModel.bindIntent(intent)
    }

    override fun observeState(state: MutableLiveData<ItemDetailViewState>) {
        state.observe(viewLifecycleOwner, Observer { value ->
            component_item_detail.renderState(value)
        })
    }

    override fun observeEvent(event: SingleLiveEvent<ItemDetailViewState>?) {
        event?.observe(viewLifecycleOwner, Observer { value ->
            component_item_detail.renderEvent(value)
        })
    }

    override fun state(): MutableLiveData<ItemDetailViewState> = mViewModel.state()

    override fun event(): SingleLiveEvent<ItemDetailViewState>? = mViewModel.event()

    private fun initEventHandler() {
        component_item_detail.mShareItemHandler = ::shareItem
    }

    private fun initActionMenu() {
        toolbar.apply {
            inflateMenu(R.menu.fragment_item_detail)
            setOnMenuItemClickListener { item ->
                setActionMenuClickListener(item.itemId)
                true
            }
        }
    }

    private fun setActionMenuClickListener(itemId: Int) {
        when (itemId) {
            R.id.action_menu_share -> {
                component_item_detail.mItem?.let {
                    mSubject.onNext(
                        ItemDetailIntent.ShareItem(it)
                    )
                }
            }
        }
    }

    private fun shareItem(text: String) {
        val intent = Intent()
            .apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            }
        startActivity(
            Intent.createChooser(intent, null)
        )
    }
}