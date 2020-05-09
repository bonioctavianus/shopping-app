package com.bonioctavianus.android.shopping_app.repository.item

import com.bonioctavianus.android.shopping_app.model.Home
import com.bonioctavianus.android.shopping_app.model.Item
import io.reactivex.Single

interface ItemRepository {
    fun getHomeItems(): Single<Home>
    fun getMockItems(): Single<List<Item>>
}

class ItemRepositoryV1(
    private val mService: ItemService,
    private val mTransformer: ItemTransformer
) : ItemRepository {

    override fun getHomeItems(): Single<Home> {
        return mService.getHomeItems()
            .map { response ->
                mTransformer.transform(response.first())
            }
    }

    override fun getMockItems(): Single<List<Item>> {
        return Single.just(
            listOf(
                Item(
                    id = 1,
                    title = "Alfa",
                    description = "The Quick Brown Fox Jumps Over The Lazy Dog",
                    price = "$7",
                    isFavorite = false,
                    imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/67/Firefox_Logo%2C_2017.svg/512px-Firefox_Logo%2C_2017.svg.png"
                ),
                Item(
                    id = 2,
                    title = "Bravo",
                    description = "The Quick Brown Fox Jumps Over The Lazy Dog",
                    price = "$7",
                    isFavorite = false,
                    imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/67/Firefox_Logo%2C_2017.svg/512px-Firefox_Logo%2C_2017.svg.png"
                ),
                Item(
                    id = 3,
                    title = "Charlie",
                    description = "The Quick Brown Fox Jumps Over The Lazy Dog",
                    price = "$7",
                    isFavorite = false,
                    imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/67/Firefox_Logo%2C_2017.svg/512px-Firefox_Logo%2C_2017.svg.png"
                ),
                Item(
                    id = 4,
                    title = "Delta",
                    description = "The Quick Brown Fox Jumps Over The Lazy Dog",
                    price = "$7",
                    isFavorite = false,
                    imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/67/Firefox_Logo%2C_2017.svg/512px-Firefox_Logo%2C_2017.svg.png"
                ),
                Item(
                    id = 5,
                    title = "Echo",
                    description = "The Quick Brown Fox Jumps Over The Lazy Dog",
                    price = "$7",
                    isFavorite = false,
                    imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/67/Firefox_Logo%2C_2017.svg/512px-Firefox_Logo%2C_2017.svg.png"
                ),
                Item(
                    id = 6,
                    title = "Foxtrot",
                    description = "The Quick Brown Fox Jumps Over The Lazy Dog",
                    price = "$7",
                    isFavorite = false,
                    imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/67/Firefox_Logo%2C_2017.svg/512px-Firefox_Logo%2C_2017.svg.png"
                ),
                Item(
                    id = 7,
                    title = "Golf",
                    description = "The Quick Brown Fox Jumps Over The Lazy Dog",
                    price = "$7",
                    isFavorite = false,
                    imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/67/Firefox_Logo%2C_2017.svg/512px-Firefox_Logo%2C_2017.svg.png"
                ),
                Item(
                    id = 8,
                    title = "Hotel",
                    description = "The Quick Brown Fox Jumps Over The Lazy Dog",
                    price = "$7",
                    isFavorite = false,
                    imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/67/Firefox_Logo%2C_2017.svg/512px-Firefox_Logo%2C_2017.svg.png"
                ),
                Item(
                    id = 9,
                    title = "India",
                    description = "The Quick Brown Fox Jumps Over The Lazy Dog",
                    price = "$7",
                    isFavorite = false,
                    imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/67/Firefox_Logo%2C_2017.svg/512px-Firefox_Logo%2C_2017.svg.png"
                ),
                Item(
                    id = 10,
                    title = "Juliett",
                    description = "The Quick Brown Fox Jumps Over The Lazy Dog",
                    price = "$7",
                    isFavorite = false,
                    imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/67/Firefox_Logo%2C_2017.svg/512px-Firefox_Logo%2C_2017.svg.png"
                ),
                Item(
                    id = 11,
                    title = "Kilo",
                    description = "The Quick Brown Fox Jumps Over The Lazy Dog",
                    price = "$7",
                    isFavorite = false,
                    imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/67/Firefox_Logo%2C_2017.svg/512px-Firefox_Logo%2C_2017.svg.png"
                ),
                Item(
                    id = 12,
                    title = "Lima",
                    description = "The Quick Brown Fox Jumps Over The Lazy Dog",
                    price = "$7",
                    isFavorite = false,
                    imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/67/Firefox_Logo%2C_2017.svg/512px-Firefox_Logo%2C_2017.svg.png"
                ),
                Item(
                    id = 13,
                    title = "Mike",
                    description = "The Quick Brown Fox Jumps Over The Lazy Dog",
                    price = "$7",
                    isFavorite = false,
                    imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/67/Firefox_Logo%2C_2017.svg/512px-Firefox_Logo%2C_2017.svg.png"
                )
            )
        )
    }
}