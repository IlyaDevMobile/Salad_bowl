package com.ilyakoz.saladbowl.cleancode.application.fragmetn_presentation

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilyakoz.saladbowl.cleancode.application.domain.AddRecipeUseCase
import com.ilyakoz.saladbowl.cleancode.application.domain.RecipeItem
import kotlinx.coroutines.launch

class CreateRecipeViewModel (
    private val addRecipeUseCase: AddRecipeUseCase,
) : ViewModel() {


    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

//    private val _recipeItem = MutableLiveData<RecipeItem>()
//    val recipeItem: LiveData<RecipeItem>
//        get() = _recipeItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    private val _selectedImageUri = MutableLiveData<Uri>()
    val selectedImageUri: MutableLiveData<Uri>
        get() = _selectedImageUri


//    suspend fun getRecipeItem(recipeItemId: Int) {
//        val item = getRecipeItemUseCase.getRecipeItem(recipeItemId)
//        _recipeItem.value = item
//    }


    suspend fun addRecipeItem(
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
        val imageUri = selectedImageUri.value.toString()
        val fieldsValid = validateInput(name)
        if (fieldsValid) {
            viewModelScope.launch {
                val recipeItem = RecipeItem(
                    name,
                    ingredients,
                    description,
                    time,
                    imageUri,
                )
                addRecipeUseCase.addRecipeItem(recipeItem)
                finishWork()
            }
        }
    }


//    suspend fun editRecipeItem(
//        inputName: String?,
//        inputIngredients: String?,
//        inputDescription: String?,
//        inputTime: String?,
//        inputNameImage: String?
//    ) {
//        val name = parseText(inputName)
//        val ingredients = parseText(inputIngredients)
//        val description = parseText(inputDescription)
//        val time = parseText(inputTime)
//        val imageUri = inputNameImage
//        val fieldsValid = validateInput(name)
//        if (fieldsValid) {
//            _recipeItem.value?.let {
//                viewModelScope.launch {
//                    val item = it.copy(
//                        name = name,
//                        ingredients = ingredients,
//                        description = description,
//                        time = time,
//                        imageUri = inputNameImage
//                    )
//                    editRecipeUseCase.editRecipeItem(item)
//                    finishWork()
//                }
//            }
//        }
//    }


    private fun parseText(inputName: String?): String {
        return inputName?.trim() ?: ""
    }


//    private fun parseImage(inputNameImage: String?): String {
//        return inputNameImage ?: defaultImageUri.toString()
//    }


    private fun validateInput(name: String): Boolean {
        var result = true
        if (name.isBlank() || name.length > 40) {
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