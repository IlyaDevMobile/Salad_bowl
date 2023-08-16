package com.ilyakoz.saladbowl.cleancode.application.domain

import androidx.lifecycle.LiveData

interface SaladBowlRepository {

   suspend fun addRecipe(recipeItem: RecipeItem)
   suspend fun deleteRecipe(recipeItem: RecipeItem)
   suspend fun editRecipe(recipeItem: RecipeItem)
   suspend fun getRecipe(recipeItemId: Int) : RecipeItem

   fun getRecipeList(): LiveData<List<RecipeItem>>
}