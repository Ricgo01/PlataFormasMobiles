package com.example.lab_07.ui.meals.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.lab_07.database.categories.MealCategoryEntity
import com.example.lab_07.navigation.NavigationState
import com.example.lab_07.networking.response.meals.Meal

@Composable
fun MealCategory(meal: Meal, navController: NavController) {
    OutlinedCard(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Row {
            Image(
                painter = rememberAsyncImagePainter(meal.urlImage),
                contentDescription = null,
                modifier = Modifier
                    .size(88.dp)
                    .padding(4.dp)
            )
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(16.dp).clickable {
                        navController.navigate(NavigationState.MealDetail.createRoute(meal.id))
                    }
            ) {
                Text(
                    text = meal.name,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = meal.id,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}