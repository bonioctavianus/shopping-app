package com.bonioctavianus.android.shopping_app.usecase

import android.content.Intent
import androidx.fragment.app.Fragment
import com.bonioctavianus.android.shopping_app.repository.auth.FacebookAuthService
import com.bonioctavianus.android.shopping_app.repository.auth.FacebookSignInResult
import com.bonioctavianus.android.shopping_app.repository.user.UserService
import io.mockk.*
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DoFacebookSignInTest {

    private val mFacebookAuthService: FacebookAuthService = mockk()
    private val mUserService: UserService = mockk()
    private val mDoFacebookSignIn: DoFacebookSignIn =
        DoFacebookSignIn(mFacebookAuthService, mUserService)

    @Test
    fun `signIn() - should trigger facebook sign in`() {
        val fragment = mockk<Fragment>()

        every { mFacebookAuthService.signIn(fragment) } returns
                Observable.just(Unit)

        mDoFacebookSignIn.signIn(fragment)
            .test()
            .assertResult(Unit)
            .dispose()

        verify {
            mFacebookAuthService.signIn(fragment)
        }
    }

    @Test
    fun `handleSignInResult() - should return success`() {
        val requestCode = 101
        val resultCode = -1
        val data = mockk<Intent>()
        val userId = "user_id"
        val result = FacebookSignInResult(
            userId = userId,
            throwable = null
        )

        every { mFacebookAuthService.handleSignInResult(requestCode, resultCode, data) } returns
                Observable.just(result)

        every { mUserService.saveUserId(userId) } just Runs

        mDoFacebookSignIn.handleSignInResult(requestCode, resultCode, data)
            .test()
            .assertResult(
                Result.Success(Unit)
            )
            .dispose()

        verify {
            mFacebookAuthService.handleSignInResult(requestCode, resultCode, data)
            mUserService.saveUserId(userId)
        }
    }

    @Test
    fun `handleSignInResult() when throwing exception - should return error`() {
        val requestCode = 101
        val resultCode = -1
        val data = mockk<Intent>()
        val exception = mockk<RuntimeException>()
        val result = FacebookSignInResult(
            userId = null,
            throwable = exception
        )

        every { mFacebookAuthService.handleSignInResult(requestCode, resultCode, data) } returns
                Observable.just(result)

        mDoFacebookSignIn.handleSignInResult(requestCode, resultCode, data)
            .test()
            .assertResult(
                Result.Error(exception)
            )
            .dispose()

        verify {
            mFacebookAuthService.handleSignInResult(requestCode, resultCode, data)
        }

        verify(exactly = 0) {
            mUserService.saveUserId(any())
        }
    }
}