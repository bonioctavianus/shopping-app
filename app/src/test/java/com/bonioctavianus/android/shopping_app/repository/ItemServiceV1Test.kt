package com.bonioctavianus.android.shopping_app.repository

import com.bonioctavianus.android.shopping_app.repository.item.HomeResponse
import com.bonioctavianus.android.shopping_app.repository.item.ItemApi
import com.bonioctavianus.android.shopping_app.repository.item.ItemService
import com.bonioctavianus.android.shopping_app.repository.item.ItemServiceV1
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ItemServiceV1Test {

    private val mApi: ItemApi = mockk()
    private val mService: ItemService = ItemServiceV1(mApi)

    @Test
    fun `getHomeItems - should get home items`() {
        val response = mockk<List<HomeResponse>>()

        every { mApi.getHomeItems() } returns
                Single.just(response)

        mService.getHomeItems()
            .test()
            .assertResult(response)
            .dispose()

        verify {
            mApi.getHomeItems()
        }
    }
}