package com.example.lab_07

import android.app.Application
import androidx.room.Room
import com.example.lab_07.database.AppDatabase
import com.example.lab_07.networking.webservices.MealsWebServices
import com.example.lab_07.ui.categories.repository.MealsCategoryRepository
import com.example.lab_07.ui.supermarket.repository.SuperMarketRepository

class MyApp : Application() {

    // Singleton instance of the Room database
    private lateinit var database: AppDatabase
        private set

    lateinit var categoryRepository: MealsCategoryRepository
        private set

    lateinit var supermarketRepository: SuperMarketRepository
        private set

    lateinit var categoryWebService: MealsWebServices
        private set

    override fun onCreate() {
        super.onCreate()

        // Initialize the web service
        categoryWebService = MealsWebServices()

        // Initialize Room database
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app-database"
        ).build()

        // Initialize repositories with the corresponding DAOs
        categoryRepository = MealsCategoryRepository(
            categoryWebService,
            database.mealCategoryDao()
        )

        supermarketRepository = SuperMarketRepository(
            database.supermarketItemDao()
        )
    }
}