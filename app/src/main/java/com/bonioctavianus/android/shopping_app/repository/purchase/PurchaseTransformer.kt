package com.bonioctavianus.android.shopping_app.repository.purchase

import com.bonioctavianus.android.shopping_app.model.Item
import com.bonioctavianus.android.shopping_app.utils.TimeHelper
import javax.inject.Inject

class PurchaseTransformer @Inject constructor(
    private val mTimeHelper: TimeHelper
) {

    fun transform(item: Item): ItemEntity {
        with(item) {
            return ItemEntity(
                timestamp = mTimeHelper.getCurrentTime(),
                id = id,
                title = title,
                description = description,
                price = price,
                isFavorite = if (isFavorite) 1 else 0,
                imageUrl = imageUrl
            )
        }
    }

    fun transform(entities: List<ItemEntity>): List<Item> {
        return entities.map(::transform)
    }

    fun transform(entity: ItemEntity): Item {
        with(entity) {
            return Item(
                id = id,
                title = title,
                description = description,
                price = price,
                isFavorite = isFavorite == 1,
                imageUrl = imageUrl
            )
        }
    }
}