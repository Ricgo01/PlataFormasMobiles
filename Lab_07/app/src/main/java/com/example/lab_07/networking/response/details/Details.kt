package com.example.lab_07.networking.response.details

import com.google.gson.annotations.SerializedName

data class Details (
    @SerializedName("idMeal") val id: String,
    @SerializedName("strMeal") val name: String,
    @SerializedName("strMealThumb") val urlImage: String,
    @SerializedName("strInstructions") val instructions: String
)