package com.bonioctavianus.android.shopping_app.repository.purchase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bonioctavianus.android.shopping_app.repository.purchase.ItemEntity.Companion.TABLE_NAME
import io.reactivex.Maybe

@Dao
interface PurchaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: ItemEntity): Maybe<Long>

    @Query(value = "SELECT * FROM $TABLE_NAME ORDER BY timestamp DESC")
    fun getAllItems(): Maybe<List<ItemEntity>>

    @Query(value = "SELECT * FROM $TABLE_NAME WHERE id = :id")
    fun getItem(id: Int): Maybe<ItemEntity>

    @Query(value = "DELETE FROM $TABLE_NAME")
    fun deleteAllItems(): Maybe<Int>
}