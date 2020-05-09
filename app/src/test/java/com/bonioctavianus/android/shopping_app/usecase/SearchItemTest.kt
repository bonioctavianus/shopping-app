package com.bonioctavianus.android.shopping_app.usecase

import com.bonioctavianus.android.shopping_app.model.Item
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
class SearchItemTest {

    private val mRepository: ItemRepository = mockk()
    private val mScheduler: SchedulerPool = mockk()
    private val mSearchItem: SearchItem = SearchItem(mRepository, mScheduler)

    @Before
    fun setup() {
        every { mScheduler.io() } returns Schedulers.trampoline()
        every { mScheduler.ui() } returns Schedulers.trampoline()
    }

    @Test
    fun `search() - should return matched items`() {
        val items = listOf(
            Item(
                id = 1,
                title = "Nintendo",
                description = "",
                price = "$400",
                isFavorite = false,
                imageUrl = ""
            )
        )
        val title = "Nintendo"

        every { mRepository.getMockItems() } returns
                Single.just(items)

        mSearchItem.search(title)
            .test()
            .assertResult(
                Result.Success(items)
            )
            .dispose()

        verify {
            mRepository.getMockItems()
        }
    }

    @Test
    fun `search() when throwing exception - should return error`() {
        val exception = mockk<RuntimeException>()
        val title = "Nintendo"

        every { mRepository.getMockItems() } returns
                Single.error(exception)

        mSearchItem.search(title)
            .test()
            .assertResult(
                Result.Error(exception)
            )
            .dispose()

        verify {
            mRepository.getMockItems()
        }
    }
}