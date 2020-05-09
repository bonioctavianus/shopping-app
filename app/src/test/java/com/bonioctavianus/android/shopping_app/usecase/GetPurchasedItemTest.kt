package com.bonioctavianus.android.shopping_app.usecase

import com.bonioctavianus.android.shopping_app.model.Item
import com.bonioctavianus.android.shopping_app.network.SchedulerPool
import com.bonioctavianus.android.shopping_app.repository.purchase.PurchaseRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetPurchasedItemTest {

    private val mRepository: PurchaseRepository = mockk()
    private val mScheduler: SchedulerPool = mockk()
    private val mGetPurchasedItem: GetPurchasedItem = GetPurchasedItem(mRepository, mScheduler)

    @Before
    fun setup() {
        every { mScheduler.io() } returns Schedulers.trampoline()
        every { mScheduler.ui() } returns Schedulers.trampoline()
    }

    @Test
    fun `getItems() - should return list of purchased items`() {
        val expected = mockk<List<Item>>()

        every { mRepository.getAllItems() } returns
                Maybe.just(expected)

        mGetPurchasedItem.getItems()
            .test()
            .assertResult(
                Result.InFlight,
                Result.Success(expected)
            )
            .dispose()

        verify {
            mRepository.getAllItems()
        }
    }

    @Test
    fun `getItems() when throwing exception - should return error`() {
        val exception = mockk<RuntimeException>()

        every { mRepository.getAllItems() } returns
                Maybe.error(exception)

        mGetPurchasedItem.getItems()
            .test()
            .assertResult(
                Result.InFlight,
                Result.Error(exception)
            )
            .dispose()

        verify {
            mRepository.getAllItems()
        }
    }
}