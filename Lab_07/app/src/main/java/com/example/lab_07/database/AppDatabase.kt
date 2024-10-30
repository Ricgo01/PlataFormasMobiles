package com.example.lab_07.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lab_07.database.categories.MealCategoryDao
import com.example.lab_07.database.categories.MealCategoryEntity
import com.example.lab_07.database.categories.SuperMarketDao
import com.example.lab_07.database.categories.SupermarketItemEntity


@Database(entities = [MealCategoryEntity::class, SupermarketItemEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mealCategoryDao(): MealCategoryDao
    abstract fun supermarketItemDao(): SuperMarketDao
}