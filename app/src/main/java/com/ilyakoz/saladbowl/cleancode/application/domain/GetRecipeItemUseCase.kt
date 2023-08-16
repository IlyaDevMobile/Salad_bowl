package com.ilyakoz.saladbowl.cleancode.application.domain

class GetRecipeItemUseCase(private val saladBowlRepository: SaladBowlRepository) {

    suspend fun getRecipeItem(recipeItemId: Int): RecipeItem {
        return saladBowlRepository.getRecipe(recipeItemId)
    }
}