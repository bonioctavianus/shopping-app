package com.bonioctavianus.android.shopping_app.ui

import androidx.navigation.fragment.findNavController
import com.bonioctavianus.android.shopping_app.model.Item
import com.bonioctavianus.android.shopping_app.ui.auth.AuthFragment
import com.bonioctavianus.android.shopping_app.ui.auth.AuthFragmentDirections
import com.bonioctavianus.android.shopping_app.ui.home.HomeFragment
import com.bonioctavianus.android.shopping_app.ui.home.HomeFragmentDirections
import com.bonioctavianus.android.shopping_app.ui.profile.ProfileFragment
import com.bonioctavianus.android.shopping_app.ui.profile.ProfileFragmentDirections
import com.bonioctavianus.android.shopping_app.ui.splash.SplashFragment
import com.bonioctavianus.android.shopping_app.ui.splash.SplashFragmentDirections
import javax.inject.Inject

class Navigator @Inject constructor() {

    fun navigateToAuthFragment(fragment: SplashFragment) {
        val direction = SplashFragmentDirections.actionSplashFragmentToAuthFragment()
        fragment.findNavController().navigate(direction)
    }

    fun navigateToMainFragment(fragment: SplashFragment) {
        val direction = SplashFragmentDirections.actionSplashFragmentToMainFragment()
        fragment.findNavController().navigate(direction)
    }

    fun navigateToMainFragment(fragment: AuthFragment) {
        val direction = AuthFragmentDirections.actionAuthFragmentToMainFragment()
        fragment.findNavController().navigate(direction)
    }

    fun navigateToSearchFragment(fragment: HomeFragment) {
        val direction = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
        fragment.findNavController().navigate(direction)
    }

    fun navigateToItemDetailFragment(fragment: HomeFragment, item: Item) {
        val direction = HomeFragmentDirections.actionHomeFragmentToItemDetailFragment(item)
        fragment.findNavController().navigate(direction)
    }

    fun navigateToItemDetailFragment(fragment: ProfileFragment, item: Item) {
        val direction = ProfileFragmentDirections.actionProfileFragmentToItemDetailFragment(item)
        fragment.findNavController().navigate(direction)
    }
}