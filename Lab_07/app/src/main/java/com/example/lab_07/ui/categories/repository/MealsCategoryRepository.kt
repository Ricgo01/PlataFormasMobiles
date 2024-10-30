package com.example.lab_07.ui.categories.repository

import com.example.lab_07.database.categories.MealCategoryDao
import com.example.lab_07.database.categories.MealCategoryEntity
import com.example.lab_07.networking.webservices.MealsWebServices
import com.example.lab_07.networking.response.categories.toEntity


class MealsCategoryRepository(private val webService: MealsWebServices,
                              private val mealCategoryDao: MealCategoryDao
) {
    suspend fun getMealsCategories(): List<MealCategoryEntity> {
        val entities = webService.getMealsCategories().categories
        val content = entities.map { it.toEntity() }
        mealCategoryDao.insertAll(content)
        return mealCategoryDao.getAllMealCategories()
    }
}