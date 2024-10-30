package com.example.lab_07.ui.mealsdetail.viewmodel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab_07.networking.response.details.Details
import com.example.lab_07.networking.response.details.DetailsResponse
import com.example.lab_07.networking.response.meals.Meal
import com.example.lab_07.ui.mealsdetail.repository.DetailsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailsViewModel(
    private val repository: DetailsRepository = DetailsRepository()
) : ViewModel() {

    // LiveData para almacenar los detalles de la comida
    private val _mealDetails = MutableLiveData<Details>() // Cambiado a Details
    val mealDetails: LiveData<Details> = _mealDetails

    fun loadDetail(IdMeal: String){
        viewModelScope.launch {
            try {
                val response = repository.MealsDetails(IdMeal)

                response.enqueue(object: Callback<DetailsResponse>{
                    override fun onResponse(
                        call: Call<DetailsResponse>,
                        response: Response<DetailsResponse>
                    ){
                        if(response.isSuccessful){
                            _mealDetails.value = response.body()?.details?.firstOrNull()
                        } else {
                            Log.e("DetailsViewModel", "Error: ${response.code()}")
                        }
                    }
                    override fun onFailure(call: Call<DetailsResponse>, t: Throwable){
                        Log.e("DetailsViewModel", "Error: ${t.message}")
                    }
                })
            } catch (e: Exception){
                Log.e("DetailsViewModel", "Error: ${e.message}")
            }
        }

    }

}