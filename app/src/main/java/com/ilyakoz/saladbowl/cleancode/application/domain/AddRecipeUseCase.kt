package com.ilyakoz.saladbowl.cleancode.application.domain

import android.net.Uri

class AddRecipeUseCase(private val saladBowlRepository: SaladBowlRepository) {

    suspend fun addRecipeItem(recipeItem: RecipeItem) {
        saladBowlRepository.addRecipe(recipeItem)

    }

}