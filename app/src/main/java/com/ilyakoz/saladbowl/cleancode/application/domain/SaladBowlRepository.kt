package com.ilyakoz.saladbowl.cleancode.application.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SaladBowlRepository {


   fun getRecipeList(): Flow<List<RecipeItem>>


   suspend fun addRecipe(recipeItem: RecipeItem)
   suspend fun deleteRecipe(recipeItem: RecipeItem)
   suspend fun editRecipe(recipeItem: RecipeItem)
   suspend fun getRecipe(recipeItemId: Int) : RecipeItem


}