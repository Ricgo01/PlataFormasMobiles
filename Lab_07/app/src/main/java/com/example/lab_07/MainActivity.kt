package com.example.lab_07

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.lab_07.navigation.Navigation
import com.example.lab_07.ui.categories.viewmodel.MealViewModelFactory
import com.example.lab_07.ui.categories.viewmodel.MealsCategoriesViewModel
import com.example.lab_07.ui.supermarket.viewmodel.SuperMarketViewModel
import com.example.lab_07.ui.supermarket.viewmodel.SuperMarketViewModelFactory
import com.example.lab_07.ui.theme.Lab_07Theme

class MainActivity : ComponentActivity() {

    private lateinit var mealViewModel: MealsCategoriesViewModel
    private lateinit var supermarketViewModel: SuperMarketViewModel // <-- Cambiado a minúscula

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = (applicationContext as MyApp).categoryRepository
        val repository2 = (applicationContext as MyApp).supermarketRepository

        mealViewModel = ViewModelProvider(
            this,
            MealViewModelFactory(repository)
        )[MealsCategoriesViewModel::class.java]

        supermarketViewModel = ViewModelProvider( // <-- Cambiado aquí también
            this,
            SuperMarketViewModelFactory(repository2)
        )[SuperMarketViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            Lab_07Theme {
                Navigation(
                    navController = rememberNavController(),
                    mealViewModel = mealViewModel,
                    supaViewModel = supermarketViewModel // <-- Cambiado aquí también
                )
            }
        }
    }
}
