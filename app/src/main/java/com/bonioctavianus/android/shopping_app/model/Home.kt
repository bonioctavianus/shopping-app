package com.bonioctavianus.android.shopping_app.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Home(
    val categories: List<Category>,
    val items: List<Item>
)

data class Category(
    val id: Int,
    val name: String,
    val imageUrl: String
)

@Parcelize
data class Item(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val isFavorite: Boolean,
    val imageUrl: String
) : Parcelable