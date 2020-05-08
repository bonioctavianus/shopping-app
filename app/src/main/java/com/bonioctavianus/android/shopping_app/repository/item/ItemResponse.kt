package com.bonioctavianus.android.shopping_app.repository.item

import com.google.gson.annotations.SerializedName

data class HomeResponse(
    @SerializedName(value = "data") val data: DataResponse?
)

data class DataResponse(
    @SerializedName(value = "category") val categories: List<CategoryResponse>?,
    @SerializedName(value = "productPromo") val items: List<ItemResponse>?
)

data class CategoryResponse(
    @SerializedName(value = "id") val id: Int?,
    @SerializedName(value = "name") val name: String?,
    @SerializedName(value = "imageUrl") val imageUrl: String?
)

data class ItemResponse(
    @SerializedName(value = "id") val id: String?,
    @SerializedName(value = "title") val title: String?,
    @SerializedName(value = "description") val description: String?,
    @SerializedName(value = "price") val price: String?,
    @SerializedName(value = "loved") val isLiked: Int?,
    @SerializedName(value = "imageUrl") val imageUrl: String?
)