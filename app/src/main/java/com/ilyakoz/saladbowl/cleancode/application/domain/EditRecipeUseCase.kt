package com.ilyakoz.saladbowl.cleancode.application.domain


class EditRecipeUseCase(private val saladBowlRepository: SaladBowlRepository) {


    suspend fun editRecipeItem(recipeItem: RecipeItem, imageUri: String) {
        saladBowlRepository.editRecipe(recipeItem, imageUri)
    }
}