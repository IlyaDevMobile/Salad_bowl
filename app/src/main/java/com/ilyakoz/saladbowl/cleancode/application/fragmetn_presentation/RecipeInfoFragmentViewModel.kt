package com.ilyakoz.saladbowl.cleancode.application.fragmetn_presentation

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ilyakoz.saladbowl.cleancode.application.domain.GetRecipeItemUseCase
import com.ilyakoz.saladbowl.cleancode.application.domain.RecipeItem

class RecipeInfoFragmentViewModel (
    private val getRecipeItemUseCase: GetRecipeItemUseCase
) : ViewModel() {


    private val _recipeItem = MutableLiveData<RecipeItem>()
    val recipeItem: LiveData<RecipeItem>
        get() = _recipeItem

    private val _selectedImageUri = MutableLiveData<Uri>()
    val selectedImageUri: MutableLiveData<Uri>
        get() = _selectedImageUri


    suspend fun getRecipeItem(recipeItemId: Int) {
        val item = getRecipeItemUseCase.getRecipeItem(recipeItemId)
        _recipeItem.value = item
    }


}
