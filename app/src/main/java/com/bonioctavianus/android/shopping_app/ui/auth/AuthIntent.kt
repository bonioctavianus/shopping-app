package com.bonioctavianus.android.shopping_app.ui.auth

import android.content.Intent
import androidx.fragment.app.Fragment
import com.bonioctavianus.android.shopping_app.ui.base.MviIntent

sealed class AuthIntent : MviIntent {
    data class DoFacebookSignIn(val fragment: Fragment) : AuthIntent()
    data class HandleFacebookSignInResult(
        val requestCode: Int,
        val resultCode: Int,
        val data: Intent?
    ) : AuthIntent()

    data class DoGoogleSignIn(val fragment: Fragment) : AuthIntent()
    data class HandleGoogleSignInResult(val data: Intent?) : AuthIntent()
    data class DoEmailSignIn(val email: String, val password: String) : AuthIntent()
}