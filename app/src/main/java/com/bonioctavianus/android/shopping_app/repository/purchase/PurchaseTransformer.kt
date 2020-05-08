package com.bonioctavianus.android.shopping_app.repository.purchase

import com.bonioctavianus.android.shopping_app.model.Item
import javax.inject.Inject

class PurchaseTransformer @Inject constructor() {

    fun transform(item: Item): ItemEntity {
        with(item) {
            return ItemEntity(
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