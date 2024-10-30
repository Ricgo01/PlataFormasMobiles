package com.example.lab_07.ui.meals.repository

import com.example.lab_07.networking.response.meals.Meal
import com.example.lab_07.networking.webservices.MealsWebServices


class MealsRepository(private val webService: MealsWebServices = MealsWebServices()) {

    suspend fun filterByCategory(category: String): List<Meal> {
        return webService.filterMealsByCategory(category).meals
    }
}