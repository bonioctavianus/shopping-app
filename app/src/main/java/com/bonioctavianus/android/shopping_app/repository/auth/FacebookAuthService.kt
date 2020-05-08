package com.bonioctavianus.android.shopping_app.repository.auth

import android.content.Intent
import androidx.fragment.app.Fragment
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.Profile
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import io.reactivex.Observable
import javax.inject.Inject

class FacebookAuthService @Inject constructor(
    private val mCallbackManager: CallbackManager
) {

    fun signIn(fragment: Fragment): Observable<Unit> {
        return Observable.fromCallable {
            LoginManager.getInstance().logInWithReadPermissions(fragment, listOf("public_profile"))
        }
    }

    fun handleSignInResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ): Observable<FacebookSignInResult> {
        return Observable.create { emitter ->

            LoginManager.getInstance()
                .registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
                    override fun onSuccess(result: LoginResult?) {
                        emitter.onNext(
                            FacebookSignInResult(userId = Profile.getCurrentProfile().id)
                        )
                    }

                    override fun onCancel() {
                        emitter.onNext(
                            FacebookSignInResult(error = "Operation cancelled")
                        )
                    }

                    override fun onError(error: FacebookException?) {
                        emitter.onNext(
                            FacebookSignInResult(error = error?.message)
                        )
                    }
                })

            mCallbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun signOut(): Observable<Unit> {
        return Observable.fromCallable {
            LoginManager.getInstance().logOut()
        }
    }
}

data class FacebookSignInResult(
    val userId: String? = null,
    val error: String? = null
)