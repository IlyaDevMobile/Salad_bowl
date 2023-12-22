package com.ilyakoz.saladbowl.cleancode.application.domain

import kotlinx.coroutines.flow.Flow

class GetListRecipeUseCase(private val saladBowlRepository: SaladBowlRepository) {



    operator fun invoke(): Flow<List<RecipeItem>> {
        return saladBowlRepository.getRecipeList()
    }

}