package com.ilyakoz.saladbowl.presentation.createNewRecipe

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilyakoz.saladbowl.data.SaladBowlRepositoryImpl
import com.ilyakoz.saladbowl.domain.AddRecipeUseCase
import com.ilyakoz.saladbowl.domain.EditRecipeUseCase
import com.ilyakoz.saladbowl.domain.GetRecipeItemUseCase
import com.ilyakoz.saladbowl.domain.RecipeItem

class CreateRecipeViewModel(application : Application) : AndroidViewModel(application) {

    private val repository = SaladBowlRepositoryImpl(application)

    private val getRecipeItemUseCase = GetRecipeItemUseCase(repository)
    private val addRecipeUseCase = AddRecipeUseCase(repository)
    private val editRecipeUseCase = EditRecipeUseCase(repository)

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
            val recipeItem = RecipeItem(name, ingredients, inputImage, inputDescription)
            addRecipeUseCase.addRecipeItem(recipeItem)
            finishWork()

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