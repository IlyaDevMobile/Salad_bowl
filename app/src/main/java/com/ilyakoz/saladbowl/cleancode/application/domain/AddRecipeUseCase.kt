package com.ilyakoz.saladbowl.cleancode.application.domain


class AddRecipeUseCase(private val saladBowlRepository: SaladBowlRepository) {

    suspend fun addRecipeItem(recipeItem: RecipeItem) {
        saladBowlRepository.addRecipe(recipeItem)

    }

}