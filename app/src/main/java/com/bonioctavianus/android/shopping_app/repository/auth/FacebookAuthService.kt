package com.bonioctavianus.android.shopping_app.repository.auth

import android.content.Intent
import androidx.fragment.app.Fragment
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
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
                        handleLoginSuccess(emitter)
                    }

                    override fun onCancel() {
                        emitter.onNext(
                            FacebookSignInResult(
                                throwable = RuntimeException("Operation cancelled")
                            )
                        )
                    }

                    override fun onError(error: FacebookException?) {
                        emitter.onNext(
                            FacebookSignInResult(
                                throwable = RuntimeException(error?.message ?: "Unknown error")
                            )
                        )
                    }
                })

            mCallbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun handleLoginSuccess(emitter: ObservableEmitter<FacebookSignInResult>) {
        val userId = Profile.getCurrentProfile()?.id

        if (userId == null) {
            object : ProfileTracker() {
                override fun onCurrentProfileChanged(
                    oldProfile: Profile?,
                    currentProfile: Profile?
                ) {
                    this.stopTracking()
                    emitter.onNext(
                        FacebookSignInResult(userId = currentProfile?.id)
                    )
                }
            }

        } else {
            emitter.onNext(
                FacebookSignInResult(userId = userId)
            )
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
    val throwable: Throwable? = null
)