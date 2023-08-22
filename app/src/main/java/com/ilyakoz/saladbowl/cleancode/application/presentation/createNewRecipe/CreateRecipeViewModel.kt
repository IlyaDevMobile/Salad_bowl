package com.ilyakoz.saladbowl.cleancode.application.presentation.createNewRecipe

import android.net.Uri
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

    private val _selectedImageUri = MutableLiveData<Uri>()
    val selectedImageUri: LiveData<Uri>
        get() = _selectedImageUri

    suspend fun getRecipeItem(recipeItemId: Int) {
        val item = getRecipeItemUseCase.getRecipeItem(recipeItemId)
        _recipeItem.value = item
    }

    fun setSelectedImageUri(uri: Uri) {
        _selectedImageUri.value = uri
    }


    private val defaultImageUri =
        Uri.parse("android.resource://${com.ilyakoz.saladbowl.R.drawable.ic_emptyphoto}")


    suspend fun addRecipeItem(
        inputName: String?,
        inputIngredients: String?,
        inputDescription: String?,
        inputNameImage: String?
    ) {
        val name = parseText(inputName)
        val ingredients = parseText(inputIngredients)
        val description = parseText(inputDescription)
        val fieldsValid = validateInput(name)
        if (fieldsValid) {
            viewModelScope.launch {
                val imageUri = selectedImageUri.value ?: defaultImageUri
                val recipeItem = RecipeItem(
                    name,
                    ingredients,
                    description,
                    imageUri.toString(),
                )
                addRecipeUseCase.addRecipeItem(recipeItem, imageUri)
                finishWork()
            }
        }
    }




    suspend fun editRecipeItem(
        inputName: String?,
        inputIngredients: String?,
        inputDescription: String?,
        inputNameImage: String?
    ) {
        val name = parseText(inputName)
        val ingredients = parseText(inputIngredients)
        val description = parseText(inputDescription)
        val imageUri = parseText(inputNameImage)
        val fieldsValid = validateInput(name)
        val image: Uri? =
            _selectedImageUri.value
        if (fieldsValid && image != null) {
            _recipeItem.value?.let {
                viewModelScope.launch {
                    val item = it.copy(
                        name = name,
                        ingredients = ingredients,
                        description = description,
                        imageUri = imageUri
                    )
                    editRecipeUseCase.editRecipeItem (item,imageUri)
                    finishWork()
                }
            }
        }
    }


    private fun parseText(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun validateInput(name: String): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
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
