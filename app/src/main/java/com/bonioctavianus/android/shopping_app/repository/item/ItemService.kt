package com.bonioctavianus.android.shopping_app.repository.item

import io.reactivex.Single

interface ItemService {
    fun getHomeItems(): Single<List<HomeResponse>>
}

class ItemServiceV1(private val mApi: ItemApi) : ItemService {

    override fun getHomeItems(): Single<List<HomeResponse>> {
        return mApi.getHomeItems()
    }
}