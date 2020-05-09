package com.bonioctavianus.android.shopping_app.viewmodel

import android.content.Intent
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bonioctavianus.android.shopping_app.ui.auth.AuthIntent
import com.bonioctavianus.android.shopping_app.ui.auth.AuthInteractor
import com.bonioctavianus.android.shopping_app.ui.auth.AuthViewModel
import com.bonioctavianus.android.shopping_app.ui.auth.AuthViewState
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AuthViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mStateObserver: Observer<AuthViewState> = mockk(relaxed = true)
    private val mEventObserver: Observer<AuthViewState> = mockk(relaxed = true)
    private val mInteractor: AuthInteractor = mockk()
    private val mViewModel: AuthViewModel = AuthViewModel(mInteractor)

    @Before
    fun setup() {
        mViewModel.state().observeForever(mStateObserver)
        mViewModel.event()?.observeForever(mEventObserver)
    }

    @Test
    fun `bindIntent() for DoEmailSignIn intent - should return Success state`() {
        val email = "jane.doe@gmail.com"
        val password = "admin"

        val intent: Observable<AuthIntent> =
            Observable.just(
                AuthIntent.DoEmailSignIn(email, password)
            )

        val composer = ObservableTransformer<AuthIntent, AuthViewState> {
            Observable.just(
                AuthViewState.EmailSignIn.InFlight,
                AuthViewState.EmailSignIn.Success
            )
        }

        every { mInteractor.compose() } returns composer

        mViewModel.bindIntent(intent)

        verify {
            mInteractor.compose()
            mStateObserver.onChanged(AuthViewState.EmailSignIn.InFlight)
            mEventObserver.onChanged(AuthViewState.EmailSignIn.Success)
        }
    }

    @Test
    fun `bindIntent() for DoEmailSignIn intent when throwing exception - should return Error state`() {
        val email = "jane.doe@gmail.com"
        val password = "admin"
        val exception = mockk<RuntimeException>()

        val intent: Observable<AuthIntent> =
            Observable.just(
                AuthIntent.DoEmailSignIn(email, password)
            )

        val composer = ObservableTransformer<AuthIntent, AuthViewState> {
            Observable.just(
                AuthViewState.EmailSignIn.InFlight,
                AuthViewState.EmailSignIn.Error(exception)
            )
        }

        every { mInteractor.compose() } returns composer

        mViewModel.bindIntent(intent)

        verify {
            mInteractor.compose()
            mStateObserver.onChanged(AuthViewState.EmailSignIn.InFlight)
            mStateObserver.onChanged(AuthViewState.EmailSignIn.Error(exception))
        }
    }

    @Test
    fun `bindIntent() for DoFacebookSignIn intent - should return InFlight state`() {
        val fragment = mockk<Fragment>()

        val intent: Observable<AuthIntent> =
            Observable.just(
                AuthIntent.DoFacebookSignIn(fragment)
            )

        val composer = ObservableTransformer<AuthIntent, AuthViewState> {
            Observable.just(
                AuthViewState.FacebookSignIn.InFlight
            )
        }

        every { mInteractor.compose() } returns composer

        mViewModel.bindIntent(intent)

        verify {
            mInteractor.compose()
            mStateObserver.onChanged(AuthViewState.FacebookSignIn.InFlight)
        }
    }

    @Test
    fun `bindIntent() for DoGoogleSignIn intent - should return InFlight state`() {
        val fragment = mockk<Fragment>()

        val intent: Observable<AuthIntent> =
            Observable.just(
                AuthIntent.DoGoogleSignIn(fragment)
            )

        val composer = ObservableTransformer<AuthIntent, AuthViewState> {
            Observable.just(
                AuthViewState.GoogleSignIn.InFlight
            )
        }

        every { mInteractor.compose() } returns composer

        mViewModel.bindIntent(intent)

        verify {
            mInteractor.compose()
            mStateObserver.onChanged(AuthViewState.GoogleSignIn.InFlight)
        }
    }

    @Test
    fun `bindIntent() for HandleFacebookSignInResult intent - should return Success state`() {
        val requestCode = 101
        val resultCode = -1
        val data = mockk<Intent>()

        val intent: Observable<AuthIntent> =
            Observable.just(
                AuthIntent.HandleFacebookSignInResult(requestCode, resultCode, data)
            )

        val composer = ObservableTransformer<AuthIntent, AuthViewState> {
            Observable.just(
                AuthViewState.FacebookSignIn.Success
            )
        }

        every { mInteractor.compose() } returns composer

        mViewModel.bindIntent(intent)

        verify {
            mInteractor.compose()
            mEventObserver.onChanged(AuthViewState.FacebookSignIn.Success)
        }
    }

    @Test
    fun `bindIntent() for HandleFacebookSignInResult intent when throwing exception - should return Error state`() {
        val requestCode = 101
        val resultCode = -1
        val data = mockk<Intent>()
        val exception = mockk<RuntimeException>()

        val intent: Observable<AuthIntent> =
            Observable.just(
                AuthIntent.HandleFacebookSignInResult(requestCode, resultCode, data)
            )

        val composer = ObservableTransformer<AuthIntent, AuthViewState> {
            Observable.just(
                AuthViewState.FacebookSignIn.Error(exception)
            )
        }

        every { mInteractor.compose() } returns composer

        mViewModel.bindIntent(intent)

        verify {
            mInteractor.compose()
            mStateObserver.onChanged(AuthViewState.FacebookSignIn.Error(exception))
        }
    }

    @Test
    fun `bindIntent() for HandleGoogleSignInResult intent - should return Success state`() {
        val data = mockk<Intent>()

        val intent: Observable<AuthIntent> =
            Observable.just(
                AuthIntent.HandleGoogleSignInResult(data)
            )

        val composer = ObservableTransformer<AuthIntent, AuthViewState> {
            Observable.just(
                AuthViewState.GoogleSignIn.Success
            )
        }

        every { mInteractor.compose() } returns composer

        mViewModel.bindIntent(intent)

        verify {
            mInteractor.compose()
            mEventObserver.onChanged(AuthViewState.GoogleSignIn.Success)
        }
    }

    @Test
    fun `bindIntent() for HandleGoogleSignInResult intent when throwing exception - should return Error state`() {
        val data = mockk<Intent>()
        val exception = mockk<RuntimeException>()

        val intent: Observable<AuthIntent> =
            Observable.just(
                AuthIntent.HandleGoogleSignInResult(data)
            )

        val composer = ObservableTransformer<AuthIntent, AuthViewState> {
            Observable.just(
                AuthViewState.GoogleSignIn.Error(exception)
            )
        }

        every { mInteractor.compose() } returns composer

        mViewModel.bindIntent(intent)

        verify {
            mInteractor.compose()
            mStateObserver.onChanged(AuthViewState.GoogleSignIn.Error(exception))
        }
    }
}