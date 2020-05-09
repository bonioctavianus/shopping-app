package com.bonioctavianus.android.shopping_app.usecase

import com.bonioctavianus.android.shopping_app.network.SchedulerPool
import com.bonioctavianus.android.shopping_app.repository.purchase.PurchaseRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetPurchasedItem @Inject constructor(
    private val mRepository: PurchaseRepository,
    private val mScheduler: SchedulerPool
) {
    fun getItems(): Observable<Result> {
        return mRepository.getAllItems()
            .toObservable()
            .map { items -> Result.Success(items) }
            .cast(Result::class.java)
            .startWith(Result.InFlight)
            .onErrorReturn { throwable -> Result.Error(throwable) }
            .subscribeOn(mScheduler.io())
            .observeOn(mScheduler.ui())
    }
}