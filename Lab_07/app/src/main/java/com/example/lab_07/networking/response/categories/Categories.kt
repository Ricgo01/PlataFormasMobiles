package com.example.lab_07.networking.response.categories

import com.example.lab_07.database.categories.MealCategoryEntity
import com.google.gson.annotations.SerializedName

data class Categories(
    @SerializedName("idCategory") val id: String,
    @SerializedName("strCategory") val name: String?,
    @SerializedName("strCategoryDescription") val description: String,
    @SerializedName("strCategoryThumb") val imageUrl: String
)

fun Categories.toEntity(): MealCategoryEntity {
    return MealCategoryEntity(
        id = this.id,
        name = this.name ?: "",
        imageUrl = this.imageUrl,
        description = this.description
    )
}