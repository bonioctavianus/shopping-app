package com.bonioctavianus.android.shopping_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.bonioctavianus.android.shopping_app.extensions.setupWithNavController
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private var currentNavController: LiveData<NavController>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBottomNavigationView()
    }

    private fun initBottomNavigationView() {
        val navGraphIds = listOf(
            R.navigation.nav_graph_home,
            R.navigation.nav_graph_feed,
            R.navigation.nav_graph_cart,
            R.navigation.nav_graph_profile
        )

        val controller = bottom_navigation_view.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = childFragmentManager,
            containerId = R.id.nav_host_container
        )
        currentNavController = controller
    }

}