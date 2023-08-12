package com.ilyakoz.saladbowl.domain

class GetRecipeUseCase(private val saladBowlRepository: SaladBowlRepository) {

    suspend fun getRecipe(recipeItemId: Int): RecipeItem {
        return saladBowlRepository.getRecipe(recipeItemId)
    }
}