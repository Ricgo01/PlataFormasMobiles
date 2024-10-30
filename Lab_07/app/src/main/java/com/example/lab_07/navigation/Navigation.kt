package com.example.lab_07.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lab_07.ui.categories.view.CategoryScreen
import com.example.lab_07.ui.categories.viewmodel.MealsCategoriesViewModel
import com.example.lab_07.ui.meals.view.MealsFilterScreen
import com.example.lab_07.ui.mealsdetail.view.DetailsScreen
import com.example.lab_07.ui.supermarket.view.SupermarketFormScreen
import com.example.lab_07.ui.supermarket.view.SupermarketListScreen
import com.example.lab_07.ui.supermarket.viewmodel.SuperMarketViewModel

@Composable
fun Navigation(
    navController: NavHostController,
    mealViewModel: MealsCategoriesViewModel,
    supaViewModel: SuperMarketViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavigationState.MealsCategories.route,
        modifier = modifier
    ) {
        composable(route = NavigationState.MealsCategories.route) {
            CategoryScreen(navController = navController, viewModel = mealViewModel)
        }

        composable(
            route = NavigationState.MealsRecipesList.route,
            arguments = listOf(navArgument("category") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("category") ?: ""
            MealsFilterScreen(navController = navController, category = categoryName)
        }

        composable(
            route = NavigationState.MealDetail.route,
            arguments = listOf(navArgument("mealId") { type = NavType.StringType })
        ) { backStackEntry ->
            val mealId = backStackEntry.arguments?.getString("mealId") ?: ""
            DetailsScreen(navController = navController, mealId = mealId)
        }

        composable(route = NavigationState.SupermarketListScreen.route) {
            SupermarketListScreen(
                navController = navController,
                viewModel = supaViewModel,
                onNavigateToForm = { item ->
                    val route = NavigationState.SupermarketForm.createRoute(item?.id)
                    navController.navigate(route)
                }
            )
        }

        composable(
            route = NavigationState.SupermarketForm.route,
            arguments = listOf(navArgument("itemId") { type = NavType.IntType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("itemId") ?: -1
            val item = if (itemId != -1) supaViewModel.getItemById(itemId) else null

            SupermarketFormScreen(
                navController = navController,
                viewModel = supaViewModel,
                item = item
            )
        }
    }
}