package com.ilyakoz.saladbowl.domain

class AddRecipeUseCase(private val saladBowlRepository: SaladBowlRepository) {

    suspend fun addRecipeItem(recipeItem: RecipeItem) {
        saladBowlRepository.addRecipe(recipeItem)

    }

}