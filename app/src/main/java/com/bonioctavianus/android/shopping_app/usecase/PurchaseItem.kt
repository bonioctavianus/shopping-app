package com.bonioctavianus.android.shopping_app.usecase

import com.bonioctavianus.android.shopping_app.model.Item
import com.bonioctavianus.android.shopping_app.network.SchedulerPool
import com.bonioctavianus.android.shopping_app.repository.purchase.PurchaseRepository
import io.reactivex.Observable
import javax.inject.Inject

class PurchaseItem @Inject constructor(
    private val mRepository: PurchaseRepository,
    private val mScheduler: SchedulerPool
) {
    fun purchase(item: Item): Observable<Result> {
        return mRepository.insert(item)
            .toObservable()
            .map { Result.Success(Unit) }
            .cast(Result::class.java)
            .onErrorReturn { throwable -> Result.Error(throwable) }
            .subscribeOn(mScheduler.io())
            .observeOn(mScheduler.ui())
    }
}