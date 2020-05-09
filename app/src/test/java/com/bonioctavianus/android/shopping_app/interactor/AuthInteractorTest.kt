package com.bonioctavianus.android.shopping_app.interactor

import android.content.Intent
import androidx.fragment.app.Fragment
import com.bonioctavianus.android.shopping_app.ui.auth.AuthIntent
import com.bonioctavianus.android.shopping_app.ui.auth.AuthInteractor
import com.bonioctavianus.android.shopping_app.ui.auth.AuthViewState
import com.bonioctavianus.android.shopping_app.usecase.DoEmailSignIn
import com.bonioctavianus.android.shopping_app.usecase.DoFacebookSignIn
import com.bonioctavianus.android.shopping_app.usecase.DoGoogleSignIn
import com.bonioctavianus.android.shopping_app.usecase.Result
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AuthInteractorTest {

    private val mDoFacebookSignIn: DoFacebookSignIn = mockk()
    private val mDoGoogleSignIn: DoGoogleSignIn = mockk()
    private val mDoEmailSignIn: DoEmailSignIn = mockk()
    private val mInteractor: AuthInteractor =
        AuthInteractor(mDoFacebookSignIn, mDoGoogleSignIn, mDoEmailSignIn)

    @Test
    fun `composing DoEmailSignIn intent - should return in success state`() {
        val email = "jane.doe@gmail.com"
        val password = "admin"

        every { mDoEmailSignIn.signIn(email, password) } returns
                Observable.just(
                    Result.InFlight,
                    Result.Success(Unit)
                )

        Observable.just(
            AuthIntent.DoEmailSignIn(email, password)
        )
            .compose(mInteractor.compose())
            .test()
            .assertResult(
                AuthViewState.EmailSignIn.InFlight,
                AuthViewState.EmailSignIn.Success
            )
            .dispose()

        verify {
            mDoEmailSignIn.signIn(email, password)
        }
    }

    @Test
    fun `composing DoEmailSignIn intent when throwing exception - should return error state`() {
        val email = "jane.doe@gmail.com"
        val password = "admin"
        val exception = mockk<RuntimeException>()

        every { mDoEmailSignIn.signIn(email, password) } returns
                Observable.just(
                    Result.InFlight,
                    Result.Error(exception)
                )

        Observable.just(
            AuthIntent.DoEmailSignIn(email, password)
        )
            .compose(mInteractor.compose())
            .test()
            .assertResult(
                AuthViewState.EmailSignIn.InFlight,
                AuthViewState.EmailSignIn.Error(exception)
            )
            .dispose()

        verify {
            mDoEmailSignIn.signIn(email, password)
        }
    }

    @Test
    fun `composing DoFacebookSignIn intent - should return in flight state`() {
        val fragment = mockk<Fragment>()

        every { mDoFacebookSignIn.signIn(fragment) } returns
                Observable.just(Unit)

        Observable.just(
            AuthIntent.DoFacebookSignIn(fragment)
        )
            .compose(mInteractor.compose())
            .test()
            .assertResult(
                AuthViewState.FacebookSignIn.InFlight
            )
            .dispose()

        verify {
            mDoFacebookSignIn.signIn(fragment)
        }
    }

    @Test
    fun `composing HandleFacebookSignInResult intent - should return success state`() {
        val requestCode = 101
        val resultCode = -1
        val data = mockk<Intent>()

        every { mDoFacebookSignIn.handleSignInResult(requestCode, resultCode, data) } returns
                Observable.just(
                    Result.Success(Unit)
                )

        Observable.just(
            AuthIntent.HandleFacebookSignInResult(requestCode, resultCode, data)
        )
            .compose(mInteractor.compose())
            .test()
            .assertResult(
                AuthViewState.FacebookSignIn.Success
            )
            .dispose()

        verify {
            mDoFacebookSignIn.handleSignInResult(requestCode, resultCode, data)
        }
    }

    @Test
    fun `composing HandleFacebookSignInResult intent when throwing exception - should return error state`() {
        val requestCode = 101
        val resultCode = -1
        val data = mockk<Intent>()
        val exception = mockk<RuntimeException>()

        every { mDoFacebookSignIn.handleSignInResult(requestCode, resultCode, data) } returns
                Observable.just(
                    Result.Error(exception)
                )

        Observable.just(
            AuthIntent.HandleFacebookSignInResult(requestCode, resultCode, data)
        )
            .compose(mInteractor.compose())
            .test()
            .assertResult(
                AuthViewState.FacebookSignIn.Error(exception)
            )
            .dispose()

        verify {
            mDoFacebookSignIn.handleSignInResult(requestCode, resultCode, data)
        }
    }

    @Test
    fun `composing DoGoogleSignIn intent - should return in flight state`() {
        val fragment = mockk<Fragment>()

        every { mDoGoogleSignIn.signIn(fragment) } returns
                Observable.just(Unit)

        Observable.just(
            AuthIntent.DoGoogleSignIn(fragment)
        )
            .compose(mInteractor.compose())
            .test()
            .assertResult(
                AuthViewState.GoogleSignIn.InFlight
            )
            .dispose()

        verify {
            mDoGoogleSignIn.signIn(fragment)
        }
    }

    @Test
    fun `composing HandleGoogleSignInResult intent - should return success state`() {
        val data = mockk<Intent>()

        every { mDoGoogleSignIn.handleSignInResult(data) } returns
                Observable.just(
                    Result.Success(Unit)
                )

        Observable.just(
            AuthIntent.HandleGoogleSignInResult(data)
        )
            .compose(mInteractor.compose())
            .test()
            .assertResult(
                AuthViewState.GoogleSignIn.Success
            )
            .dispose()

        verify {
            mDoGoogleSignIn.handleSignInResult(data)
        }
    }

    @Test
    fun `composing HandleGoogleSignInResult intent when throwing exception - should return error state`() {
        val data = mockk<Intent>()
        val exception = mockk<RuntimeException>()

        every { mDoGoogleSignIn.handleSignInResult(data) } returns
                Observable.just(
                    Result.Error(exception)
                )

        Observable.just(
            AuthIntent.HandleGoogleSignInResult(data)
        )
            .compose(mInteractor.compose())
            .test()
            .assertResult(
                AuthViewState.GoogleSignIn.Error(exception)
            )
            .dispose()

        verify {
            mDoGoogleSignIn.handleSignInResult(data)
        }
    }
}