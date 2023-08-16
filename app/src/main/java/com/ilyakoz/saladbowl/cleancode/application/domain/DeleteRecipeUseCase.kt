package com.ilyakoz.saladbowl.cleancode.application.domain

class DeleteRecipeUseCase(private val saladBowlRepository: SaladBowlRepository) {


    suspend fun deleteRecipe(recipeItem: RecipeItem) {
        saladBowlRepository.deleteRecipe(recipeItem)
    }

}

