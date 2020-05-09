package com.bonioctavianus.android.shopping_app.usecase

import com.bonioctavianus.android.shopping_app.network.SchedulerPool
import com.bonioctavianus.android.shopping_app.repository.item.ItemRepository
import io.reactivex.Observable
import javax.inject.Inject

class SearchItem @Inject constructor(
    private val mRepository: ItemRepository,
    private val mScheduler: SchedulerPool
) {
    fun search(title: String): Observable<Result> {
        return mRepository.getMockItems()
            .toObservable()
            .map { items ->
                items.filter { item ->
                    item.title.contains(title, ignoreCase = true)
                }
            }
            .map { items -> Result.Success(items) }
            .cast(Result::class.java)
            .onErrorReturn { throwable -> Result.Error(throwable) }
            .subscribeOn(mScheduler.io())
            .observeOn(mScheduler.ui())
    }
}