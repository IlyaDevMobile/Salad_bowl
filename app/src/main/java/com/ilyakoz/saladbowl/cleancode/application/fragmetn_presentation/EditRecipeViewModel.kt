package com.ilyakoz.saladbowl.cleancode.application.fragmetn_presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilyakoz.saladbowl.cleancode.application.domain.AddRecipeUseCase
import com.ilyakoz.saladbowl.cleancode.application.domain.EditRecipeUseCase
import com.ilyakoz.saladbowl.cleancode.application.domain.GetRecipeItemUseCase
import com.ilyakoz.saladbowl.cleancode.application.domain.RecipeItem
import kotlinx.coroutines.launch

class EditRecipeViewModel(
    private val getRecipeItemUseCase: GetRecipeItemUseCase,
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

    suspend fun editRecipeItem(
        inputName: String?,
        inputIngredients: String?,
        inputDescription: String?,
        inputTime: String?,
        inputNameImage: String?
    ) {
        val name = parseText(inputName)
        val ingredients = parseText(inputIngredients)
        val description = parseText(inputDescription)
        val time = parseText(inputTime)
        val imageUri = inputNameImage
        val fieldsValid = validateInput(name)
        if (fieldsValid) {
            _recipeItem.value?.let {
                viewModelScope.launch {
                    val item = it.copy(
                        name = name,
                        ingredients = ingredients,
                        description = description,
                        time = time,
                        imageUri = inputNameImage
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

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }

    private fun validateInput(name: String): Boolean {
        var result = true
        if (name.isBlank() || name.length > 40) {
            _errorInputName.value = true
            result = false
        }
        return result
    }




}