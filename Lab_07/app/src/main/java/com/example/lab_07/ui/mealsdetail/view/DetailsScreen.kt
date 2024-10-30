package com.example.lab_07.ui.mealsdetail.view


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.lab_07.navigation.AppBar
import com.example.lab_07.ui.meals.view.MealCategory
import com.example.lab_07.ui.mealsdetail.viewmodel.DetailsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailsScreen(navController: NavController, mealId: String) {
    // ViewModel que se encarga de cargar los detalles de la comida
    val viewModel: DetailsViewModel = viewModel()

    LaunchedEffect(mealId) {
        viewModel.loadDetail(mealId)
    }

    val mealDetails = viewModel.mealDetails.value

    Scaffold(topBar = {
        AppBar(titulo = "Meal Details", navController = navController)
    }) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(100.dp))
            LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {

                mealDetails?.let { details ->
                    item {
                        MealDetail(details, navController)
                    }

                }
            }
        }
    }
}