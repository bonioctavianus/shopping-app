package com.bonioctavianus.android.shopping_app.repository.purchase

import com.bonioctavianus.android.shopping_app.model.Item
import io.reactivex.Maybe

interface PurchaseRepository {
    fun insert(item: Item): Maybe<Long>
    fun getAllItems(): Maybe<List<Item>>
    fun getItem(id: Int): Maybe<Item>
}

class PurchaseRepositoryV1(
    private val mService: PurchaseService,
    private val mTransformer: PurchaseTransformer
) : PurchaseRepository {

    override fun insert(item: Item): Maybe<Long> {
        return mService.insert(
            mTransformer.transform(item)
        )
    }

    override fun getAllItems(): Maybe<List<Item>> {
        return mService.getAllItems()
            .map { entities -> mTransformer.transform(entities) }
    }

    override fun getItem(id: Int): Maybe<Item> {
        return mService.getItem(id)
            .map { entity -> mTransformer.transform(entity) }
    }
}