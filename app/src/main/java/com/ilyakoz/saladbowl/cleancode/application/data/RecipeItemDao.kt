package com.ilyakoz.saladbowl.cleancode.application.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow


@Dao
interface RecipeItemDao {


    @Query("SELECT * FROM recipe_items")
    fun getRecipeList(): Flow<List<RecipeItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipe(recipeItemDbModel: RecipeItemDbModel)

    @Query("DELETE FROM recipe_items WHERE id=:recipeItemId")
    suspend fun deleteRecipe(recipeItemId: Int)

    @Query("SELECT * FROM recipe_items WHERE id=:recipeItemId LIMIT 1")
    suspend fun getRecipe(recipeItemId: Int): RecipeItemDbModel
}