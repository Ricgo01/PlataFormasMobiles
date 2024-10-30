package com.example.lab_07.database.categories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MealCategoryDao {

    /*
    * OnConflictStrategy.REPLACE (use latest value)
    * OnConflictStrategy.ABORT  (avoid duplicates)
    * OnConflictStrategy.IGNORE (use initial value)
    * OnConflictStrategy.FAIL (transaction rollback) no exception
    * OnConflictStrategy.ROLLBACK (roll back current transaction)
    * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(mealCategories: List<MealCategoryEntity>)

    @Query("SELECT * FROM meal_categories")
    suspend fun getAllMealCategories(): List<MealCategoryEntity>
}