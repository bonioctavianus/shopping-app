package com.bonioctavianus.android.shopping_app.usecase

import android.content.Intent
import androidx.fragment.app.Fragment
import com.bonioctavianus.android.shopping_app.repository.auth.GoogleAuthService
import com.bonioctavianus.android.shopping_app.repository.auth.GoogleSignInResult
import com.bonioctavianus.android.shopping_app.repository.user.UserService
import io.mockk.*
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DoGoogleSignInTest {

    private val mGoogleAuthService: GoogleAuthService = mockk()
    private val mUserService: UserService = mockk()
    private val mDoGoogleSignIn: DoGoogleSignIn = DoGoogleSignIn(mGoogleAuthService, mUserService)

    @Test
    fun `signIn() - should trigger google sign in`() {
        val fragment = mockk<Fragment>()

        every { mGoogleAuthService.signIn(fragment) } returns
                Observable.just(Unit)

        mDoGoogleSignIn.signIn(fragment)
            .test()
            .assertResult(Unit)
            .dispose()

        verify {
            mGoogleAuthService.signIn(fragment)
        }
    }

    @Test
    fun `handleSignInResult() - should return success`() {
        val data = mockk<Intent>()
        val userId = "user_id"
        val result = GoogleSignInResult(
            userId = userId,
            throwable = null
        )

        every { mGoogleAuthService.handleSignInResult(data) } returns
                Observable.just(result)

        every { mUserService.saveUserId(userId) } just Runs

        mDoGoogleSignIn.handleSignInResult(data)
            .test()
            .assertResult(
                Result.Success(Unit)
            )
            .dispose()

        verify {
            mGoogleAuthService.handleSignInResult(data)
            mUserService.saveUserId(userId)
        }
    }

    @Test
    fun `handleSignInResult() when throwing exception - should return error`() {
        val data = mockk<Intent>()
        val exception = mockk<RuntimeException>()
        val result = GoogleSignInResult(
            userId = null,
            throwable = exception
        )

        every { mGoogleAuthService.handleSignInResult(data) } returns
                Observable.just(result)

        mDoGoogleSignIn.handleSignInResult(data)
            .test()
            .assertResult(
                Result.Error(exception)
            )
            .dispose()

        verify {
            mGoogleAuthService.handleSignInResult(data)
        }

        verify(exactly = 0) {
            mUserService.saveUserId(any())
        }
    }
}
