package com.bonioctavianus.android.shopping_app.repository

import com.bonioctavianus.android.shopping_app.model.Item
import com.bonioctavianus.android.shopping_app.repository.purchase.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Maybe
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PurchaseRepositoryV1Test {

    private val mService: PurchaseService = mockk()
    private val mTransformer: PurchaseTransformer = mockk()
    private val mRepository: PurchaseRepository = PurchaseRepositoryV1(mService, mTransformer)

    @Test
    fun `insert() - should return inserted item id`() {
        val entity = mockk<ItemEntity>()
        val item = mockk<Item>()
        val expected: Long = 1

        every { mService.insert(entity) } returns
                Maybe.just(expected)

        every { mTransformer.transform(item) } returns entity

        mRepository.insert(item)
            .test()
            .assertResult(expected)
            .dispose()

        verify {
            mService.insert(entity)
            mTransformer.transform(item)
        }
    }

    @Test
    fun `getAllItems() - should return list of items`() {
        val entities = mockk<List<ItemEntity>>()
        val items = mockk<List<Item>>()

        every { mService.getAllItems() } returns
                Maybe.just(entities)

        every { mTransformer.transform(entities) } returns items

        mRepository.getAllItems()
            .test()
            .assertResult(items)
            .dispose()

        verify {
            mService.getAllItems()
            mTransformer.transform(entities)
        }
    }

    @Test
    fun `getItem() - should return item`() {
        val itemId = 1
        val entity = mockk<ItemEntity>()
        val item = mockk<Item>()

        every { mService.getItem(itemId) } returns
                Maybe.just(entity)

        every { mTransformer.transform(entity) } returns item

        mRepository.getItem(itemId)
            .test()
            .assertResult(item)
            .dispose()

        verify {
            mService.getItem(itemId)
            mTransformer.transform(entity)
        }
    }

    @Test
    fun `deleteAllItems() - should return deleted items count`() {
        val itemCount = 1

        every { mService.deleteAllItems() } returns
                Maybe.just(itemCount)

        mRepository.deleteAllItems()
            .test()
            .assertResult(itemCount)
            .dispose()

        verify {
            mService.deleteAllItems()
        }
    }
}