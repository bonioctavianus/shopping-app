package com.bonioctavianus.android.shopping_app.ui.splash

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.bonioctavianus.android.shopping_app.repository.user.UserService
import com.bonioctavianus.android.shopping_app.ui.Navigator
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class SplashFragment : Fragment() {

    @Inject
    lateinit var mNavigator: Navigator
    @Inject
    lateinit var mUserService: UserService

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!mUserService.isUserSignedIn()) {
            mNavigator.navigateToAuthFragment(this)
        } else {
            mNavigator.navigateToMainFragment(this)
        }
    }
}