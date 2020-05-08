package com.bonioctavianus.android.shopping_app.usecase

import com.bonioctavianus.android.shopping_app.repository.user.UserService
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DoEmailSignIn @Inject constructor(
    private val mUserService: UserService
) {

    fun signIn(email: String, password: String): Observable<Result> {
        return Observable.just(
            Result.Success(Unit)
        )
            .doOnNext { mUserService.saveUserId("$email-$password") }
            .cast(Result::class.java)
            // emulate real network request
            .delay(3, TimeUnit.SECONDS)
            .startWith(Result.InFlight)
    }
}