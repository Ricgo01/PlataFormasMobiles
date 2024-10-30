package com.example.lab_07.ui.supermarket.repository

import com.example.lab_07.database.categories.SuperMarketDao
import com.example.lab_07.database.categories.SupermarketItemEntity


class SuperMarketRepository(private val dao: SuperMarketDao) {

    suspend fun getItemById(itemId: Int): SupermarketItemEntity? {
        return dao.getItemById(itemId)
    }

    suspend fun getAllItems(): List<SupermarketItemEntity> {
        return dao.getAllItems()
    }

    suspend fun insertItem(item: SupermarketItemEntity) {
        dao.insertItem(item)
    }

    suspend fun updateItem(item: SupermarketItemEntity) {
        dao.updateItem(item)
    }

    suspend fun deleteItem(item: SupermarketItemEntity) {
        dao.deleteItem(item)
    }
}