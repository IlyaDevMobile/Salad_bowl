package com.ilyakoz.saladbowl.cleancode.application.fragmetn_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilyakoz.saladbowl.cleancode.application.domain.DeleteRecipeUseCase
import com.ilyakoz.saladbowl.cleancode.application.domain.GetListRecipeUseCase
import com.ilyakoz.saladbowl.cleancode.application.domain.RecipeItem
import kotlinx.coroutines.launch


class WelcomeFragmentViewModel(
    private val getListRecipeUseCase: GetListRecipeUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase
) : ViewModel() {


    val recipeListTest = getListRecipeUseCase.invoke()


    suspend fun deleteRecipeItem(recipeItem: RecipeItem) {
        viewModelScope.launch {
            deleteRecipeUseCase.deleteRecipe(recipeItem)
        }
    }
}