package com.bonioctavianus.android.shopping_app.usecase

import com.bonioctavianus.android.shopping_app.network.SchedulerPool
import com.bonioctavianus.android.shopping_app.repository.user.UserService
import io.mockk.*
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DoEmailSignInTest {

    private val mUserService: UserService = mockk()
    private val mScheduler: SchedulerPool = mockk()
    private val mDoEmailSignIn: DoEmailSignIn = DoEmailSignIn(mUserService, mScheduler)

    @Before
    fun setup() {
        every { mScheduler.computation() } returns Schedulers.trampoline()
    }

    @Test
    fun `signIn() - should return success state`() {
        val email = "jane.doe@gmail.com"
        val password = "admin"
        val userId = "$email-$password"

        every { mUserService.saveUserId(userId) } just Runs

        mDoEmailSignIn.signIn(email, password)
            .test()
            .assertResult(
                Result.InFlight,
                Result.Success(Unit)
            )
            .dispose()

        verify {
            mUserService.saveUserId(userId)
        }
    }
}