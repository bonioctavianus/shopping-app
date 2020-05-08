package com.bonioctavianus.android.shopping_app.ui.auth

import com.bonioctavianus.android.shopping_app.ui.base.MviViewState

sealed class AuthViewState : MviViewState {

    sealed class FacebookSignIn : AuthViewState() {
        object InFlight : FacebookSignIn()
        object Success : FacebookSignIn()
        data class Error(val throwable: Throwable?) : FacebookSignIn()
    }

    sealed class GoogleSignIn : AuthViewState() {
        object InFlight : GoogleSignIn()
        object Success : GoogleSignIn()
        data class Error(val throwable: Throwable?) : GoogleSignIn()
    }

    sealed class EmailSignIn : AuthViewState() {
        object InFlight : EmailSignIn()
        object Success : EmailSignIn()
        data class Error(val throwable: Throwable?) : EmailSignIn()
    }
}