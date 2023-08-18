package com.ilyakoz.saladbowl.cleancode.application.domain

import android.net.Uri
import androidx.lifecycle.LiveData

interface SaladBowlRepository {

   suspend fun addRecipe(recipeItem: RecipeItem, imageUri: Uri)
   suspend fun deleteRecipe(recipeItem: RecipeItem)
   suspend fun editRecipe(recipeItem: RecipeItem, imageUri: String)
   suspend fun getRecipe(recipeItemId: Int) : RecipeItem

   suspend fun saveImageToFile(imageUri: Uri): String
   fun getRecipeList(): LiveData<List<RecipeItem>>
}