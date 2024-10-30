package com.example.lab_07.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(titulo: String, navController: NavController) {
    TopAppBar(
        title = {
            Text(text = titulo, color = Color.White)
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Blue),
        navigationIcon = {
            IconButton(onClick = {
                navController.navigate(NavigationState.SupermarketListScreen.route) {
                    launchSingleTop = true
                }
            }) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Supermarket List")
            }
        }
    )
}