package com.bonioctavianus.android.shopping_app.repository

import com.bonioctavianus.android.shopping_app.repository.purchase.ItemEntity
import com.bonioctavianus.android.shopping_app.repository.purchase.PurchaseDao
import com.bonioctavianus.android.shopping_app.repository.purchase.PurchaseService
import com.bonioctavianus.android.shopping_app.repository.purchase.PurchaseServiceV1
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Maybe
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PurchaseServiceV1Test {

    private val mDao: PurchaseDao = mockk()
    private val mService: PurchaseService = PurchaseServiceV1(mDao)

    @Test
    fun `insert() - should return inserted item id`() {
        val item = mockk<ItemEntity>()
        val expected: Long = 1

        every { mDao.insert(item) } returns
                Maybe.just(expected)

        mService.insert(item)
            .test()
            .assertResult(expected)
            .dispose()

        verify {
            mDao.insert(item)
        }
    }

    @Test
    fun `getAllItems() - should return list of items`() {
        val items = mockk<List<ItemEntity>>()

        every { mDao.getAllItems() } returns
                Maybe.just(items)

        mService.getAllItems()
            .test()
            .assertResult(items)
            .dispose()

        verify {
            mDao.getAllItems()
        }
    }

    @Test
    fun `getItem() - should return item`() {
        val itemId = 1
        val item = mockk<ItemEntity>()

        every { mDao.getItem(itemId) } returns
                Maybe.just(item)

        mService.getItem(itemId)
            .test()
            .assertResult(item)
            .dispose()

        verify {
            mDao.getItem(itemId)
        }
    }
    
    @Test
    fun `deleteAllItems() - should return deleted items count`() {
        val itemCount = 1

        every { mDao.deleteAllItems() } returns
                Maybe.just(itemCount)

        mService.deleteAllItems()
            .test()
            .assertResult(itemCount)
            .dispose()

        verify {
            mDao.deleteAllItems()
        }
    }
}