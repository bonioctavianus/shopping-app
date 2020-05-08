package com.bonioctavianus.android.shopping_app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bonioctavianus.android.shopping_app.repository.purchase.ItemEntity
import com.bonioctavianus.android.shopping_app.repository.purchase.PurchaseDao

@Database(entities = [ItemEntity::class], version = 1)
abstract class ShopDatabase : RoomDatabase() {

    abstract fun purchaseDao(): PurchaseDao

    companion object {
        @Volatile
        private var INSTANCE: ShopDatabase? = null

        fun getInstance(context: Context): ShopDatabase =
            INSTANCE
                ?: synchronized(this) {
                    INSTANCE
                        ?: buildDatabase(
                            context
                        ).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ShopDatabase::class.java,
                "ShopDatabase"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}