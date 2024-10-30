package com.example.lab_07.networking.response.details

import com.google.gson.annotations.SerializedName

data class DetailsResponse(
    @SerializedName("meals")val details: List<Details>
)