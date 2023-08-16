package com.ilyakoz.saladbowl.cleancode.application.presentation.createNewRecipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilyakoz.saladbowl.cleancode.application.domain.AddRecipeUseCase
import com.ilyakoz.saladbowl.cleancode.application.domain.EditRecipeUseCase
import com.ilyakoz.saladbowl.cleancode.application.domain.GetRecipeItemUseCase
import com.ilyakoz.saladbowl.cleancode.application.domain.RecipeItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CreateRecipeViewModel @Inject constructor(
    private val getRecipeItemUseCase: GetRecipeItemUseCase,
    private val addRecipeUseCase: AddRecipeUseCase,
    private val editRecipeUseCase: EditRecipeUseCase


) : ViewModel() {


    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _recipeItem = MutableLiveData<RecipeItem>()
    val recipeItem: LiveData<RecipeItem>
        get() = _recipeItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    suspend fun getRecipeItem(recipeItemId: Int) {
        val item = getRecipeItemUseCase.getRecipeItem(recipeItemId)
        _recipeItem.value = item
    }

    suspend fun addRecipeItem(
        inputName: String?,
        inputIngredients: String?,
        inputImage: String?,
        inputDescription: String?
    ) {
        val name = parseText(inputName)
        val ingredients = parseText(inputIngredients)
        val fieldsValid = validateInput(name, ingredients)
        if (fieldsValid) {
            viewModelScope.launch {
                val recipeItem = RecipeItem(name, ingredients, inputImage, inputDescription)
                addRecipeUseCase.addRecipeItem(recipeItem)
                finishWork()
            }


        }
    }

    suspend fun editRecipeItem(
        inputName: String?,
        inputIngredients: String?,
        inputDescription: String?,
        description: String?
    ) {
        val name = parseText(inputName)
        val ingredients = parseText(inputIngredients)
        val description = parseText(inputDescription)
        val fieldsValid = validateInput(name, ingredients)
        if (fieldsValid) {
            _recipeItem.value?.let {
                viewModelScope.launch {
                    val item = it.copy(
                        name = name,
                        ingredients = ingredients,
                        description = description,
                    )
                    editRecipeUseCase.editRecipeItem(item)
                    finishWork()
                }

            }


        }
    }

    private fun parseText(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun validateInput(name: String, ingredients: String): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (ingredients.isBlank()) {
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }
}