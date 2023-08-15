package com.ilyakoz.saladbowl.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface RecipeItemDao {


    @Query("SELECT * FROM recipe_items")
    fun getRecipeList(): LiveData<List<RecipeItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipe(recipeItemDbModel: RecipeItemDbModel)

    @Query("DELETE FROM recipe_items WHERE id=:recipeItemId")
    suspend fun deleteRecipe(recipeItemId: Int)

    @Query("SELECT * FROM recipe_items WHERE id=:recipeItemId LIMIT 1")
    suspend fun getRecipe(recipeItemId: Int): RecipeItemDbModel
}