package com.bonioctavianus.android.shopping_app.repository.purchase

import io.reactivex.Maybe

interface PurchaseService {
    fun insert(item: ItemEntity): Maybe<Long>
    fun getAllItems(): Maybe<List<ItemEntity>>
    fun getItem(id: Int): Maybe<ItemEntity>
    fun deleteAllItems(): Maybe<Int>
}

class PurchaseServiceV1(private val mDao: PurchaseDao) : PurchaseService {

    override fun insert(item: ItemEntity): Maybe<Long> {
        return mDao.insert(item)
    }

    override fun getAllItems(): Maybe<List<ItemEntity>> {
        return mDao.getAllItems()
    }

    override fun getItem(id: Int): Maybe<ItemEntity> {
        return mDao.getItem(id)
    }

    override fun deleteAllItems(): Maybe<Int> {
        return mDao.deleteAllItems()
    }
}