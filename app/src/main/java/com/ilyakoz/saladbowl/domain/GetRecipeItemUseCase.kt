package com.ilyakoz.saladbowl.domain

class GetRecipeItemUseCase(private val saladBowlRepository: SaladBowlRepository) {

    suspend fun getRecipeItem(recipeItemId: Int): RecipeItem {
        return saladBowlRepository.getRecipe(recipeItemId)
    }
}