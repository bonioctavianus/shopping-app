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
class PurchaseItemTest {

    private val mRepository: PurchaseRepository = mockk()
    private val mScheduler: SchedulerPool = mockk()
    private val mPurchaseItem: PurchaseItem = PurchaseItem(mRepository, mScheduler)

    @Before
    fun setup() {
        every { mScheduler.io() } returns Schedulers.trampoline()
        every { mScheduler.ui() } returns Schedulers.trampoline()
    }

    @Test
    fun `purchase() - should add item to database`() {
        val item = mockk<Item>()

        every { mRepository.insert(item) } returns
                Maybe.just(1)

        mPurchaseItem.purchase(item)
            .test()
            .assertResult(
                Result.Success(Unit)
            )
            .dispose()

        verify {
            mRepository.insert(item)
        }
    }

    @Test
    fun `purchase() when throwing exception - should return exception`() {
        val item = mockk<Item>()
        val exception = mockk<RuntimeException>()

        every { mRepository.insert(item) } returns
                Maybe.error(exception)

        mPurchaseItem.purchase(item)
            .test()
            .assertResult(
                Result.Error(exception)
            )
            .dispose()

        verify {
            mRepository.insert(item)
        }
    }
}