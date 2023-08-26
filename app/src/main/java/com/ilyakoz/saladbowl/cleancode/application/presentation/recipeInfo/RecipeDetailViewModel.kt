package com.ilyakoz.saladbowl.cleancode.application.presentation.recipeInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilyakoz.saladbowl.cleancode.application.domain.GetRecipeItemUseCase
import com.ilyakoz.saladbowl.cleancode.application.domain.RecipeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val getRecipeItemUseCase: GetRecipeItemUseCase
) : ViewModel() {


    private val _recipeItem = MutableLiveData<RecipeItem>()
    val recipeItem: LiveData<RecipeItem>
        get() = _recipeItem


    suspend fun getRecipeItem(recipeItemId: Int) {
        val item = getRecipeItemUseCase.getRecipeItem(recipeItemId)
        _recipeItem.value = item
    }
}