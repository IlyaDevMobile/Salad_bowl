package com.ilyakoz.saladbowl.domain

class AddRecipeUseCase(private val saladBowlRepository: SaladBowlRepository) {

    suspend fun addRecipe(recipeItem: RecipeItem) {
        saladBowlRepository.addRecipe(recipeItem)

    }

}