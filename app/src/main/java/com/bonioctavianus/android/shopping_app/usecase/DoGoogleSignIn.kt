package com.bonioctavianus.android.shopping_app.usecase

import android.content.Intent
import androidx.fragment.app.Fragment
import com.bonioctavianus.android.shopping_app.repository.auth.GoogleAuthService
import com.bonioctavianus.android.shopping_app.repository.user.UserService
import io.reactivex.Observable
import javax.inject.Inject

class DoGoogleSignIn @Inject constructor(
    private val mGoogleAuthService: GoogleAuthService,
    private val mUserService: UserService
) {
    fun signIn(fragment: Fragment): Observable<Unit> {
        return mGoogleAuthService.signIn(fragment)
    }

    fun handleSignInResult(data: Intent?): Observable<Result> {
        return mGoogleAuthService.handleSignInResult(data)
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