package com.ilyakoz.saladbowl.domain

import androidx.lifecycle.LiveData

class GetListRecipeUseCase(private val saladBowlRepository: SaladBowlRepository) {


    fun getRecipeList(): LiveData<List<RecipeItem>>{
        return saladBowlRepository.getRecipeList()
    }
}