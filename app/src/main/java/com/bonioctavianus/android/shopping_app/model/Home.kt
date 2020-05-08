package com.bonioctavianus.android.shopping_app.model

data class Home(
    val categories: List<Category>,
    val items: List<Item>
)

data class Category(
    val id: Int,
    val name: String,
    val imageUrl: String
)

data class Item(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val isLiked: Boolean,
    val imageUrl: String
)