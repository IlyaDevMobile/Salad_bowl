package com.ilyakoz.saladbowl.cleancode.application.domain

import android.net.Uri

class EditRecipeUseCase(private val saladBowlRepository: SaladBowlRepository) {


    suspend fun editRecipeItem(recipeItem: RecipeItem, imageUri: Uri) {
        saladBowlRepository.editRecipe(recipeItem)
    }
}