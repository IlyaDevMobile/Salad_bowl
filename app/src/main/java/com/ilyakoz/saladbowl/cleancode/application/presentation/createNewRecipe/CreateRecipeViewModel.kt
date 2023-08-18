package com.ilyakoz.saladbowl.cleancode.application.presentation.createNewRecipe

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilyakoz.saladbowl.R
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
        description: String?
    ) {
        val name = parseText(inputName)
        val ingredients = parseText(inputIngredients)
        val fieldsValid = validateInput(name)
        if (fieldsValid) {
            viewModelScope.launch {
                val imageUri = selectedImageUri.value ?: defaultImageUri
                val recipeItem = RecipeItem(
                    name,
                    ingredients,
                    imageUri.toString(),
                    inputDescription
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
        val image = parseText(inputNameImage) // Сохраняем ссылку на изображение в поле image
        val fieldsValid = validateInput(name)
        val imageUri: Uri? =
            _selectedImageUri.value // Получите выбранный URI изображения из LiveData
        if (fieldsValid && imageUri != null) {
            _recipeItem.value?.let {
                viewModelScope.launch {
                    val item = it.copy(
                        name = name,
                        ingredients = ingredients,
                        description = description,
                        image = image // Сохраните ссылку на изображение в поле image
                    )
                    editRecipeUseCase.editRecipeItem(
                        item,
                        imageUri
                    ) // Передайте параметр imageUri и null для image
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
