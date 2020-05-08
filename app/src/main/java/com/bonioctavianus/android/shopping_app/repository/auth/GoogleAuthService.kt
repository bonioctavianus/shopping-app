package com.bonioctavianus.android.shopping_app.repository.auth

import android.content.Intent
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import io.reactivex.Observable
import javax.inject.Inject

class GoogleAuthService @Inject constructor(
    private val mGoogleSignInClient: GoogleSignInClient
) {

    companion object {
        const val GOOGLE_SIGN_IN_RC = 101
    }

    fun signIn(fragment: Fragment): Observable<Unit> {
        return Observable.fromCallable {
            fragment.startActivityForResult(mGoogleSignInClient.signInIntent, GOOGLE_SIGN_IN_RC)
        }
    }

    fun handleSignInResult(intent: Intent?): Observable<GoogleSignInResult> {
        return Observable.fromCallable {
            val task = GoogleSignIn.getSignedInAccountFromIntent(intent)

            try {
                val account = task.getResult(ApiException::class.java)
                GoogleSignInResult(userId = account?.id)

            } catch (exception: ApiException) {
                GoogleSignInResult(error = exception.message)
            }
        }
    }

    fun signOut(): Observable<Unit> {
        return Observable.fromCallable {
            mGoogleSignInClient.signOut()
            Unit
        }
    }
}

data class GoogleSignInResult(
    val userId: String? = null,
    val error: String? = null
)