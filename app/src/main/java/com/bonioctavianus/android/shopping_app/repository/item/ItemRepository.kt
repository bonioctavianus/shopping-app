package com.bonioctavianus.android.shopping_app.repository.item

import com.bonioctavianus.android.shopping_app.model.Home
import io.reactivex.Single

interface ItemRepository {
    fun getHomeItems(): Single<Home>
}

class ItemRepositoryV1(
    private val mService: ItemService,
    private val mTransformer: ItemTransformer
) : ItemRepository {

    override fun getHomeItems(): Single<Home> {
        return mService.getHomeItems()
            .map { response ->
                mTransformer.transform(response.first())
            }
    }
}