package com.bonioctavianus.android.shopping_app.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bonioctavianus.android.shopping_app.R
import com.bonioctavianus.android.shopping_app.repository.user.UserService
import com.bonioctavianus.android.shopping_app.repository.user.UserServiceV1

class SplashFragment : Fragment() {

    private val mUserService: UserService = UserServiceV1()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (mUserService.isUserSignedIn()) {
            val direction = SplashFragmentDirections.actionSplashFragmentToAuthFragment()
            findNavController().navigate(direction)
        }
    }
}