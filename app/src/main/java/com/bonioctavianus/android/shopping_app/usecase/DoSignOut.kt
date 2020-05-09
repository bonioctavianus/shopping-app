package com.bonioctavianus.android.shopping_app.usecase

import com.bonioctavianus.android.shopping_app.network.SchedulerPool
import com.bonioctavianus.android.shopping_app.repository.auth.FacebookAuthService
import com.bonioctavianus.android.shopping_app.repository.auth.GoogleAuthService
import com.bonioctavianus.android.shopping_app.repository.purchase.PurchaseRepository
import com.bonioctavianus.android.shopping_app.repository.user.UserService
import io.reactivex.Observable
import io.reactivex.functions.Function4
import javax.inject.Inject

class DoSignOut @Inject constructor(
    private val mFacebookAuthService: FacebookAuthService,
    private val mGoogleAuthService: GoogleAuthService,
    private val mUserService: UserService,
    private val mPurchaseRepository: PurchaseRepository,
    private val mScheduler: SchedulerPool
) {
    fun signOut(): Observable<Result> {
        return Observable.zip(
            mFacebookAuthService.signOut(),
            mGoogleAuthService.signOut(),
            Observable.fromCallable {
                mUserService.clearUser()
                Unit
            },
            mPurchaseRepository.deleteAllItems()
                .toObservable()
                .map { Unit }
                .subscribeOn(mScheduler.io())
                .observeOn(mScheduler.ui()),
            Function4 { _: Unit, _: Unit, _: Unit, _: Unit ->
                Result.Success(Unit)
            }
        )
            .cast(Result::class.java)
            .onErrorReturn { throwable -> Result.Error(throwable) }
    }
}