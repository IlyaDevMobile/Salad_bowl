package com.ilyakoz.saladbowl.cleancode.application.domain

import android.net.Uri

class AddRecipeUseCase(private val saladBowlRepository: SaladBowlRepository) {

    suspend fun addRecipeItem(recipeItem: RecipeItem, imageUri: Uri) {
        saladBowlRepository.addRecipe(recipeItem,imageUri)

    }

}