package com.bonioctavianus.android.shopping_app.usecase

import com.bonioctavianus.android.shopping_app.network.SchedulerPool
import com.bonioctavianus.android.shopping_app.repository.auth.FacebookAuthService
import com.bonioctavianus.android.shopping_app.repository.auth.GoogleAuthService
import com.bonioctavianus.android.shopping_app.repository.purchase.PurchaseRepository
import com.bonioctavianus.android.shopping_app.repository.user.UserService
import io.mockk.*
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DoSignOutTest {

    private val mFacebookAuthService: FacebookAuthService = mockk()
    private val mGoogleAuthService: GoogleAuthService = mockk()
    private val mUserService: UserService = mockk()
    private val mPurchaseRepository: PurchaseRepository = mockk()
    private val mScheduler: SchedulerPool = mockk()
    private val mDoSignOut: DoSignOut = DoSignOut(
        mFacebookAuthService,
        mGoogleAuthService,
        mUserService,
        mPurchaseRepository,
        mScheduler
    )

    @Before
    fun setup() {
        every { mScheduler.io() } returns Schedulers.trampoline()
        every { mScheduler.ui() } returns Schedulers.trampoline()
    }

    @Test
    fun `signOut() - should sign out and clear all data`() {
        every { mFacebookAuthService.signOut() } returns
                Observable.just(Unit)

        every { mGoogleAuthService.signOut() } returns
                Observable.just(Unit)

        every { mUserService.clearUser() } just Runs

        every { mPurchaseRepository.deleteAllItems() } returns
                Maybe.just(0)

        mDoSignOut.signOut()
            .test()
            .assertResult(
                Result.Success(Unit)
            )
            .dispose()

        verify {
            mFacebookAuthService.signOut()
            mGoogleAuthService.signOut()
            mUserService.clearUser()
            mPurchaseRepository.deleteAllItems()
        }
    }

    @Test
    fun `signOut() when facebook sign out throwing exception - should return error state`() {
        val exception = mockk<RuntimeException>()

        every { mFacebookAuthService.signOut() } returns
                Observable.error(exception)

        every { mGoogleAuthService.signOut() } returns
                Observable.just(Unit)

        every { mPurchaseRepository.deleteAllItems() } returns
                Maybe.just(0)

        mDoSignOut.signOut()
            .test()
            .assertResult(
                Result.Error(exception)
            )
            .dispose()

        verify {
            mFacebookAuthService.signOut()
            mGoogleAuthService.signOut()
            mPurchaseRepository.deleteAllItems()
        }
    }

    @Test
    fun `signOut() when google sign out throwing exception - should return error state`() {
        val exception = mockk<RuntimeException>()

        every { mFacebookAuthService.signOut() } returns
                Observable.just(Unit)

        every { mGoogleAuthService.signOut() } returns
                Observable.error(exception)

        every { mPurchaseRepository.deleteAllItems() } returns
                Maybe.just(0)

        mDoSignOut.signOut()
            .test()
            .assertResult(
                Result.Error(exception)
            )
            .dispose()

        verify {
            mFacebookAuthService.signOut()
            mGoogleAuthService.signOut()
            mPurchaseRepository.deleteAllItems()
        }
    }

    @Test
    fun `signOut() when clearing database throwing exception - should return error state`() {
        val exception = mockk<RuntimeException>()

        every { mFacebookAuthService.signOut() } returns
                Observable.just(Unit)

        every { mGoogleAuthService.signOut() } returns
                Observable.just(Unit)

        every { mUserService.clearUser() } just Runs

        every { mPurchaseRepository.deleteAllItems() } returns
                Maybe.error(exception)

        mDoSignOut.signOut()
            .test()
            .assertResult(
                Result.Error(exception)
            )
            .dispose()

        verify {
            mFacebookAuthService.signOut()
            mGoogleAuthService.signOut()
            mUserService.clearUser()
            mPurchaseRepository.deleteAllItems()
        }
    }
}