package com.example.lab_07.ui.categories.viewmodel
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.lab_07.database.categories.MealCategoryEntity
import com.example.lab_07.networking.response.categories.Categories
import com.example.lab_07.ui.categories.repository.MealsCategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class MealsCategoriesViewModel(private val repository: MealsCategoryRepository): ViewModel() {

    private val _categories = MutableLiveData<List<MealCategoryEntity>>()
    val categories: LiveData<List<MealCategoryEntity>> = _categories

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun fetchCategories() {
        _isLoading.value = true
        _errorMessage.value = null
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val meals = repository.getMealsCategories()
                _categories.postValue(meals)
            } catch (e: Exception) {
                handleException(e)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    private fun handleException(exception: Exception) {
        when (exception) {
            is IOException -> _errorMessage.value = "Network error: Check your internet connection."
            else -> _errorMessage.value = "An unexpected error occurred."
        }
        // Optionally log the exception (e.g., using a logger or crash reporting tool)
        exception.printStackTrace()
    }
}

class MealViewModelFactory(private val repository: MealsCategoryRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealsCategoriesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MealsCategoriesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}