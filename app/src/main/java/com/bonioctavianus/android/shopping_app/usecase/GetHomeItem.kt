package com.bonioctavianus.android.shopping_app.usecase

import com.bonioctavianus.android.shopping_app.network.SchedulerPool
import com.bonioctavianus.android.shopping_app.repository.item.ItemRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetHomeItem @Inject constructor(
    private val mRepository: ItemRepository,
    private val mScheduler: SchedulerPool
) {
    fun getItems(): Observable<Result> {
        return mRepository.getHomeItems()
            .toObservable()
            .map { result -> Result.Success(result) }
            .cast(Result::class.java)
            .startWith(Result.InFlight)
            .onErrorReturn { throwable -> Result.Error(throwable) }
            .subscribeOn(mScheduler.io())
            .observeOn(mScheduler.ui())
    }
}