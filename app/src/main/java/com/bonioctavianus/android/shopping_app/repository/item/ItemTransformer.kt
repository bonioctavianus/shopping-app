package com.bonioctavianus.android.shopping_app.repository.item

import com.bonioctavianus.android.shopping_app.model.Category
import com.bonioctavianus.android.shopping_app.model.Home
import com.bonioctavianus.android.shopping_app.model.Item
import javax.inject.Inject

class ItemTransformer @Inject constructor() {

    fun transform(response: HomeResponse): Home {
        return Home(
            categories = response.data?.categories?.map {
                Category(
                    id = it.id ?: -1,
                    name = it.name ?: "",
                    imageUrl = it.imageUrl ?: ""
                )
            } ?: emptyList(),

            items = response.data?.items?.map {
                Item(
                    id = it.id?.toInt() ?: -1,
                    title = it.title ?: "",
                    description = it.description ?: "",
                    price = it.price ?: "",
                    isFavorite = it.isLiked == 1,
                    imageUrl = it.imageUrl ?: ""
                )
            } ?: emptyList()
        )
    }
}