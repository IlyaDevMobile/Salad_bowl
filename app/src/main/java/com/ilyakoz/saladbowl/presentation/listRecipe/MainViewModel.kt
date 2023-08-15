package com.ilyakoz.saladbowl.presentation.listRecipe

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ilyakoz.saladbowl.data.SaladBowlRepositoryImpl
import com.ilyakoz.saladbowl.domain.DeleteRecipeUseCase
import com.ilyakoz.saladbowl.domain.EditRecipeUseCase
import com.ilyakoz.saladbowl.domain.GetListRecipeUseCase
import com.ilyakoz.saladbowl.domain.RecipeItem

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SaladBowlRepositoryImpl(application)


    private val getListRecipeUseCase = GetListRecipeUseCase(repository)
    private val deleteRecipeUseCase = DeleteRecipeUseCase(repository)

    val recipeListTest = getListRecipeUseCase.getRecipeList()


    suspend fun deleteRecipeItem(recipeItem: RecipeItem) {
        deleteRecipeUseCase.deleteRecipe(recipeItem)

    }


}