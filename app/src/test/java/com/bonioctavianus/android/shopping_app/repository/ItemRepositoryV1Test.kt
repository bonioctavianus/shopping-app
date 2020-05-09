package com.bonioctavianus.android.shopping_app.repository

import com.bonioctavianus.android.shopping_app.model.Category
import com.bonioctavianus.android.shopping_app.model.Home
import com.bonioctavianus.android.shopping_app.model.Item
import com.bonioctavianus.android.shopping_app.repository.item.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ItemRepositoryV1Test {

    private val mService: ItemService = mockk()
    private val mTransformer: ItemTransformer = mockk()
    private val mRepository: ItemRepositoryV1 = ItemRepositoryV1(mService, mTransformer)

    @Test
    fun `getHomeItems() - should get home items`() {
        val response = listOf(
            HomeResponse(
                data = DataResponse(
                    categories = listOf(
                        CategoryResponse(
                            id = 1,
                            name = "Shirt",
                            imageUrl = ""
                        )
                    ),
                    items = listOf(
                        ItemResponse(
                            id = "1",
                            title = "Nintendo",
                            description = "",
                            price = "$400",
                            isLiked = 0,
                            imageUrl = ""
                        )
                    )
                )
            )
        )
        val expected = Home(
            categories = listOf(
                Category(
                    id = 1,
                    name = "Shirt",
                    imageUrl = ""
                )
            ),
            items = listOf(
                Item(
                    id = 1,
                    title = "Nintendo",
                    description = "",
                    price = "$400",
                    isFavorite = false,
                    imageUrl = ""
                )
            )
        )

        every { mService.getHomeItems() } returns
                Single.just(response)

        every { mTransformer.transform(response.first()) } returns expected

        mRepository.getHomeItems()
            .test()
            .assertResult(expected)
            .dispose()

        verify {
            mService.getHomeItems()
            mTransformer.transform(response.first())
        }
    }
}