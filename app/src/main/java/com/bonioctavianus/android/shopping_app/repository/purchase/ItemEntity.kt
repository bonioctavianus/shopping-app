package com.bonioctavianus.android.shopping_app.repository.purchase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bonioctavianus.android.shopping_app.repository.purchase.ItemEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class ItemEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "price") val price: String,
    @ColumnInfo(name = "is_favorite") val isFavorite: Int,
    @ColumnInfo(name = "image_url") val imageUrl: String
) {

    companion object {
        const val TABLE_NAME = "items"
    }
}