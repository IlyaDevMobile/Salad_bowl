package com.ilyakoz.saladbowl.cleancode.application.domain

import android.net.Uri
import androidx.lifecycle.LiveData

interface SaladBowlRepository {

   suspend fun addRecipe(recipeItem: RecipeItem, imageUri: Uri)
   suspend fun deleteRecipe(recipeItem: RecipeItem)
   suspend fun editRecipe(recipeItem: RecipeItem)
   suspend fun getRecipe(recipeItemId: Int) : RecipeItem

   fun getRecipeList(): LiveData<List<RecipeItem>>
}