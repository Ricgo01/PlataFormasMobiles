package com.example.lab_07.database.categories

import androidx.room.*

@Dao
interface SuperMarketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: SupermarketItemEntity)

    @Update
    suspend fun updateItem(item: SupermarketItemEntity)

    @Delete
    suspend fun deleteItem(item: SupermarketItemEntity)

    @Query("SELECT * FROM supermarket_items")
    suspend fun getAllItems(): List<SupermarketItemEntity>

    @Query("SELECT * FROM supermarket_items WHERE id = :itemId")
    suspend fun getItemById(itemId: Int): SupermarketItemEntity?

}