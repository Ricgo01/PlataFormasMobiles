package com.example.lab_07.ui.categories.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.example.lab_07.navigation.AppBar
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import com.example.lab_07.ui.categories.viewmodel.MealsCategoriesViewModel
import androidx.compose.ui.Modifier
import com.example.lab_07.ui.meals.view.MealCategory

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CategoryScreen(navController: NavController,
                          viewModel: MealsCategoriesViewModel) {
    val categories = viewModel.categories.observeAsState(initial = emptyList())
    val isLoading = viewModel.isLoading.observeAsState(initial = false)
    val errorMessage by viewModel.errorMessage.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchCategories()
    }

    errorMessage?.let {
        Text(text = it, color = androidx.compose.ui.graphics.Color.Red)
    }

    Scaffold(topBar = {
        AppBar(titulo = "Recipes", navController = navController)
    }) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (isLoading.value) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(categories.value) { category ->
                        CardMealCategory(category, navController)
                    }
                }
            }
        }
    }
}
