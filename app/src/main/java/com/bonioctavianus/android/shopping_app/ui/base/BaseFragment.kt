package com.bonioctavianus.android.shopping_app.ui.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bonioctavianus.android.shopping_app.R

abstract class BaseFragment<I : MviIntent, S : MviViewState> : Fragment(), MviView<I, S> {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindIntent(intents())
        observeState(state())
        observeEvent(event())
    }

    protected fun initToolbarNavigation(toolbar: Toolbar) {
        toolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener { findNavController().popBackStack() }
        }
    }
}