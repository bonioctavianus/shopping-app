package com.bonioctavianus.android.shopping_app.ui.auth

import com.bonioctavianus.android.shopping_app.usecase.DoFacebookSignIn
import com.bonioctavianus.android.shopping_app.usecase.DoGoogleSignIn
import com.bonioctavianus.android.shopping_app.usecase.Result
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val mDoFacebookSignIn: DoFacebookSignIn,
    private val mDoGoogleSignIn: DoGoogleSignIn
) {
    fun compose(): ObservableTransformer<AuthIntent, AuthViewState> {
        return ObservableTransformer { intents ->
            intents.publish { shared ->
                Observable.mergeArray(
                    shared.ofType(AuthIntent.DoFacebookSignIn::class.java)
                        .compose(doFacebookSignIn),
                    shared.ofType(AuthIntent.DoGoogleSignIn::class.java)
                        .compose(doGoogleSignIn),
                    shared.ofType(AuthIntent.HandleFacebookSignInResult::class.java)
                        .compose(handleFacebookSignInResult),
                    shared.ofType(AuthIntent.HandleGoogleSignInResult::class.java)
                        .compose(handleGoogleSignInResult)
                ).mergeWith(
                    shared.filter { intent ->
                        intent !is AuthIntent.DoFacebookSignIn
                                && intent !is AuthIntent.DoGoogleSignIn
                                && intent !is AuthIntent.HandleFacebookSignInResult
                                && intent !is AuthIntent.HandleGoogleSignInResult
                    }.flatMap { intent ->
                        Observable.error<AuthViewState>(
                            IllegalArgumentException("Unknown intent type: $intent")
                        )
                    }
                )
            }
        }
    }

    private val doFacebookSignIn =
        ObservableTransformer<AuthIntent.DoFacebookSignIn, AuthViewState> { intents ->
            intents.flatMap { intent ->
                mDoFacebookSignIn.signIn(intent.fragment)
                    .map { AuthViewState.FacebookSignInStarted }
            }
        }

    private val doGoogleSignIn =
        ObservableTransformer<AuthIntent.DoGoogleSignIn, AuthViewState> { intents ->
            intents.flatMap { intent ->
                mDoGoogleSignIn.signIn(intent.fragment)
                    .map { AuthViewState.GoogleSignInStarted }
            }
        }

    private val handleGoogleSignInResult =
        ObservableTransformer<AuthIntent.HandleGoogleSignInResult, AuthViewState> { intents ->
            intents.flatMap { intent ->
                mDoGoogleSignIn.handleSignInResult(intent.data)
                    .map { result ->
                        when (result) {
                            is Result.InFlight -> {
                                AuthViewState.GoogleSignIn.InFlight
                            }
                            is Result.Success<*> -> {
                                AuthViewState.GoogleSignIn.Success
                            }
                            is Result.Error -> {
                                AuthViewState.GoogleSignIn.Error(result.throwable)
                            }
                        }
                    }
            }
        }

    private val handleFacebookSignInResult =
        ObservableTransformer<AuthIntent.HandleFacebookSignInResult, AuthViewState> { intents ->
            intents.flatMap { intent ->
                mDoFacebookSignIn.handleSignInResult(
                    intent.requestCode,
                    intent.resultCode,
                    intent.data
                )
                    .map { result ->
                        when (result) {
                            is Result.InFlight -> {
                                AuthViewState.FacebookSignIn.InFlight
                            }
                            is Result.Success<*> -> {
                                AuthViewState.FacebookSignIn.Success
                            }
                            is Result.Error -> {
                                AuthViewState.FacebookSignIn.Error(result.throwable)
                            }
                        }
                    }
            }
        }
}