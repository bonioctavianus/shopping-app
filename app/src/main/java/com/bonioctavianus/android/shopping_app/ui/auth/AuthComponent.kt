package com.bonioctavianus.android.shopping_app.ui.auth

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.bonioctavianus.android.shopping_app.R
import io.reactivex.Observable
import kotlinx.android.synthetic.main.component_auth.view.*

class AuthComponent(context: Context, attributeSet: AttributeSet) :
    FrameLayout(context, attributeSet) {

    var mFragment: Fragment? = null
    var mSignInSuccessHandler: (() -> Unit)? = null

    init {
        View.inflate(context, R.layout.component_auth, this)
    }

    fun renderState(state: AuthViewState) {
        when (state) {
            is AuthViewState.FacebookSignInStarted -> {
                button_facebook_sign_in.isEnabled = false
            }
            is AuthViewState.GoogleSignInStarted -> {
                button_google_sign_in.isEnabled = false
            }
            is AuthViewState.FacebookSignIn.Error -> {
                button_facebook_sign_in.isEnabled = true
            }
            is AuthViewState.GoogleSignIn.Error -> {
                button_google_sign_in.isEnabled = true
            }
        }
    }

    fun renderEvent(event: AuthViewState) {
        when (event) {
            is AuthViewState.FacebookSignIn.Success -> {
                mSignInSuccessHandler?.invoke()
            }
            is AuthViewState.GoogleSignIn.Success -> {
                mSignInSuccessHandler?.invoke()
            }
        }
    }

    fun intents(): Observable<AuthIntent> {
        return Observable.merge(
            getFacebookSignInIntent(),
            getGoogleSignInIntent()
        )
    }

    private fun getFacebookSignInIntent(): Observable<AuthIntent> {
        return Observable.create { emitter ->
            button_facebook_sign_in.setOnClickListener {
                mFragment?.let {
                    emitter.onNext(AuthIntent.DoFacebookSignIn(it))
                }
            }
        }
    }

    private fun getGoogleSignInIntent(): Observable<AuthIntent> {
        return Observable.create { emitter ->
            button_google_sign_in.setOnClickListener {
                mFragment?.let {
                    emitter.onNext(AuthIntent.DoGoogleSignIn(it))
                }
            }
        }
    }
}