package com.example.lab_07.navigation

sealed class NavigationState(val route: String) {
    object MealsCategories : NavigationState("categories")

    object MealsRecipesList : NavigationState("categories/{category}") {
        fun createRoute(category: String) = "categories/$category"
    }

    object MealDetail : NavigationState("meal/{mealId}") {
        fun createRoute(mealId: String) = "meal/$mealId"
    }

    object SupermarketListScreen : NavigationState("supermarket")

    object SupermarketForm : NavigationState("supermarket/form/{itemId}") {
        fun createRoute(itemId: Int?) = "supermarket/form/${itemId ?: -1}"
    }
}