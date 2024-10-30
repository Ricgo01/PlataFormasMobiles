package com.example.lab_07.networking

import com.example.lab_07.networking.response.categories.MealsCategoriesResponse
import com.example.lab_07.networking.response.details.DetailsResponse
import com.example.lab_07.networking.response.meals.MealsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealsApi {

    @GET("categories.php")
    suspend fun getMealsCategories(): MealsCategoriesResponse

    @GET("filter.php")
    suspend fun filterByCategory(
        @Query("c") category: String
    ): MealsResponse

    @GET("lookup.php")
    fun getMealDetails(
        @Query("i") mealId: String
    ): Call<DetailsResponse>

}