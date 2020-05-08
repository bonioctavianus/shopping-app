package com.bonioctavianus.android.shopping_app.ui.auth

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.bonioctavianus.android.shopping_app.R
import com.bonioctavianus.android.shopping_app.utils.makeInvisible
import com.bonioctavianus.android.shopping_app.utils.makeVisible
import com.jakewharton.rxbinding3.view.clicks
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
            is AuthViewState.FacebookSignIn.InFlight -> {
                button_facebook_sign_in.isEnabled = false
            }
            is AuthViewState.GoogleSignIn.InFlight -> {
                button_google_sign_in.isEnabled = false
            }
            is AuthViewState.EmailSignIn.InFlight -> {
                button_sign_in.makeInvisible()
                progress_bar_sign_in.makeVisible()
            }
            is AuthViewState.FacebookSignIn.Error -> {
                button_facebook_sign_in.isEnabled = true
            }
            is AuthViewState.GoogleSignIn.Error -> {
                button_google_sign_in.isEnabled = true
            }
            is AuthViewState.EmailSignIn.Error -> {
                button_sign_in.makeVisible()
                progress_bar_sign_in.makeInvisible()
            }
        }
    }

    fun renderEvent(event: AuthViewState) {
        when (event) {
            is AuthViewState.FacebookSignIn.Success,
            is AuthViewState.GoogleSignIn.Success,
            is AuthViewState.EmailSignIn.Success -> {
                mSignInSuccessHandler?.invoke()
            }
        }
    }

    fun intents(): Observable<AuthIntent> {
        return Observable.merge(
            getFacebookSignInIntent(),
            getGoogleSignInIntent(),
            getEmailSignInIntent()
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

    private fun getEmailSignInIntent(): Observable<AuthIntent> {
        val email = input_text_email.text.toString()
        val password = input_text_password.text.toString()

        return button_sign_in.clicks()
            .map { AuthIntent.DoEmailSignIn(email, password) }
    }
}