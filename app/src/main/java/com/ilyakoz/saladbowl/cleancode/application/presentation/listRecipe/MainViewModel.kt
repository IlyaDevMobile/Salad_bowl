package com.ilyakoz.saladbowl.cleancode.application.presentation.listRecipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilyakoz.saladbowl.cleancode.application.domain.DeleteRecipeUseCase
import com.ilyakoz.saladbowl.cleancode.application.domain.GetListRecipeUseCase
import com.ilyakoz.saladbowl.cleancode.application.domain.RecipeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val getListRecipeUseCase: GetListRecipeUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase
) : ViewModel() {


    val recipeListTest = getListRecipeUseCase.getRecipeList()


    suspend fun deleteRecipeItem(recipeItem: RecipeItem) {
        viewModelScope.launch {
            deleteRecipeUseCase.deleteRecipe(recipeItem)
        }
    }
}