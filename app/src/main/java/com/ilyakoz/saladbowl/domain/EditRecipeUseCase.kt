package com.ilyakoz.saladbowl.domain

class EditRecipeUseCase(private val saladBowlRepository: SaladBowlRepository) {


    suspend fun editRecipe(recipeItem: RecipeItem) {
        saladBowlRepository.editRecipe(recipeItem)
    }
}