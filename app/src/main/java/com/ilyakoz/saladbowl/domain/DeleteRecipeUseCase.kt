package com.ilyakoz.saladbowl.domain

class DeleteRecipeUseCase(private val saladBowlRepository: SaladBowlRepository) {


    suspend fun deleteRecipe(recipeItem: RecipeItem) {
        saladBowlRepository.deleteRecipe(recipeItem)
    }

}

