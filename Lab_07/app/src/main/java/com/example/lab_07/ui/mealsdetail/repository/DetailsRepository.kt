package com.example.lab_07.ui.mealsdetail.repository

import com.example.lab_07.networking.response.details.Details
import com.example.lab_07.networking.response.details.DetailsResponse
import com.example.lab_07.networking.response.meals.Meal
import com.example.lab_07.networking.webservices.MealsWebServices
import retrofit2.Call

class DetailsRepository (private val webService: MealsWebServices = MealsWebServices())  {

    suspend fun MealsDetails(id: String): Call<DetailsResponse> {
        return webService.getMealById(id)
    }
}