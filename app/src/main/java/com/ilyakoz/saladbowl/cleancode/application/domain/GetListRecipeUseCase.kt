package com.ilyakoz.saladbowl.cleancode.application.domain

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class GetListRecipeUseCase(private val saladBowlRepository: SaladBowlRepository) {



    operator fun invoke(): Flow<List<RecipeItem>> {
        return saladBowlRepository.getRecipeList()
    }

}