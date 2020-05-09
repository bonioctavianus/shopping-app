package com.bonioctavianus.android.shopping_app.usecase

import com.bonioctavianus.android.shopping_app.model.Home
import com.bonioctavianus.android.shopping_app.network.SchedulerPool
import com.bonioctavianus.android.shopping_app.repository.item.ItemRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetHomeItemTest {

    private val mRepository: ItemRepository = mockk()
    private val mScheduler: SchedulerPool = mockk()
    private val mGetHomeItem: GetHomeItem = GetHomeItem(mRepository, mScheduler)

    @Before
    fun setup() {
        every { mScheduler.io() } returns Schedulers.trampoline()
        every { mScheduler.ui() } returns Schedulers.trampoline()
    }

    @Test
    fun `getItems() - should return list of item`() {
        val expected = mockk<Home>()

        every { mRepository.getHomeItems() } returns
                Single.just(expected)

        mGetHomeItem.getItems()
            .test()
            .assertResult(
                Result.InFlight,
                Result.Success(expected)
            )
            .dispose()

        verify {
            mRepository.getHomeItems()
        }
    }

    @Test
    fun `getItems() when throwing exception - should return error`() {
        val exception = mockk<RuntimeException>()

        every { mRepository.getHomeItems() } returns
                Single.error(exception)

        mGetHomeItem.getItems()
            .test()
            .assertResult(
                Result.InFlight,
                Result.Error(exception)
            )
            .dispose()

        verify {
            mRepository.getHomeItems()
        }
    }
}