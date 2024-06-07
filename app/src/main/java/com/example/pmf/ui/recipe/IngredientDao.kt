package com.example.pmf.ui.recipe

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface IngredientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ingredient: Ingredient)

    @Query("SELECT * FROM ingredient")
    fun getAllIngredients(): List<Ingredient>
}