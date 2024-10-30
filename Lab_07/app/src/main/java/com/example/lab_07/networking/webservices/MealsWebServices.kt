package com.example.lab_07.networking.webservices

import com.example.lab_07.networking.MealsApi
import com.example.lab_07.networking.response.categories.MealsCategoriesResponse
import com.example.lab_07.networking.response.details.DetailsResponse
import com.example.lab_07.networking.response.meals.MealsResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MealsWebServices {

    private var api: MealsApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(MealsApi::class.java)
    }

    suspend fun getMealsCategories(): MealsCategoriesResponse {
        return api.getMealsCategories()
    }

    suspend fun filterMealsByCategory(category: String): MealsResponse {
        return api.filterByCategory(category)
    }

    fun getMealById(id: String): Call<DetailsResponse> {
        return api.getMealDetails(id)
    }

}