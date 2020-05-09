package com.bonioctavianus.android.shopping_app.repository

import com.bonioctavianus.android.shopping_app.model.Item
import com.bonioctavianus.android.shopping_app.repository.purchase.ItemEntity
import com.bonioctavianus.android.shopping_app.repository.purchase.PurchaseTransformer
import com.bonioctavianus.android.shopping_app.utils.TimeHelper
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

class PurchaseTransformerTest {

    private val mTimeHelper: TimeHelper = mockk()
    private val mTransformer: PurchaseTransformer = PurchaseTransformer(mTimeHelper)

    @Test
    fun `transform item model - should return correct item entity`() {
        val timestamp = 100L

        val item = Item(
            id = 1,
            title = "Nintendo",
            description = "",
            price = "$400",
            isFavorite = false,
            imageUrl = ""
        )

        every { mTimeHelper.getCurrentTime() } returns timestamp

        val entity = mTransformer.transform(item)

        assert(entity.id == 1)
        assert(entity.title == "Nintendo")
        assert(entity.description == "")
        assert(entity.price == "$400")
        assert(entity.isFavorite == 0)
        assert(entity.imageUrl == "")
    }

    @Test
    fun `transform item entities - should return correct items model`() {
        val entities = listOf(
            ItemEntity(
                timestamp = 100L,
                id = 1,
                title = "Nintendo",
                description = "",
                price = "$400",
                isFavorite = 0,
                imageUrl = ""
            )
        )

        val items = mTransformer.transform(entities)

        assert(items[0].id == 1)
        assert(items[0].title == "Nintendo")
        assert(items[0].description == "")
        assert(items[0].price == "$400")
        assert(!items[0].isFavorite)
        assert(items[0].imageUrl == "")
    }

    @Test
    fun `transform item entity - should return correct item model`() {
        val entity = ItemEntity(
            timestamp = 100L,
            id = 1,
            title = "Nintendo",
            description = "",
            price = "$400",
            isFavorite = 0,
            imageUrl = ""
        )

        val item = mTransformer.transform(entity)

        assert(item.id == 1)
        assert(item.title == "Nintendo")
        assert(item.description == "")
        assert(item.price == "$400")
        assert(!item.isFavorite)
        assert(item.imageUrl == "")
    }
}