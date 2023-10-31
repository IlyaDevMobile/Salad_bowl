package com.ilyakoz.saladbowl.cleancode.application.di

import com.ilyakoz.saladbowl.cleancode.application.presentation.createNewRecipe.CreateRecipeViewModel
import com.ilyakoz.saladbowl.cleancode.application.presentation.listRecipe.MainViewModel
import com.ilyakoz.saladbowl.cleancode.application.presentation.recipeInfo.RecipeDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    viewModel<MainViewModel> {
        MainViewModel(
            getListRecipeUseCase = get(),
            deleteRecipeUseCase = get()
        )
    }
    viewModel<CreateRecipeViewModel> {
        CreateRecipeViewModel(get(), get(), get())
    }
    viewModel<RecipeDetailViewModel> {
        RecipeDetailViewModel(get())
    }
}
