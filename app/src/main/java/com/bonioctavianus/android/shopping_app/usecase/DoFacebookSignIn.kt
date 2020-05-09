package com.bonioctavianus.android.shopping_app.usecase

import android.content.Intent
import androidx.fragment.app.Fragment
import com.bonioctavianus.android.shopping_app.repository.auth.FacebookAuthService
import com.bonioctavianus.android.shopping_app.repository.user.UserService
import io.reactivex.Observable
import javax.inject.Inject

class DoFacebookSignIn @Inject constructor(
    private val mFacebookAuthService: FacebookAuthService,
    private val mUserService: UserService
) {
    fun signIn(fragment: Fragment): Observable<Unit> {
        return mFacebookAuthService.signIn(fragment)
    }

    fun handleSignInResult(requestCode: Int, resultCode: Int, data: Intent?): Observable<Result> {
        return mFacebookAuthService.handleSignInResult(requestCode, resultCode, data)
            .doOnNext { result ->
                result.userId?.let {
                    mUserService.saveUserId(it)
                }
            }
            .map { result ->
                when {
                    result.userId != null -> Result.Success(Unit)
                    else -> Result.Error(result.throwable)
                }
            }
    }
}