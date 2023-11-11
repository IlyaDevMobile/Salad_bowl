package com.ilyakoz.saladbowl.cleancode.application.di

import com.ilyakoz.saladbowl.cleancode.application.fragmetn_presentation.EditRecipeViewModel
import com.ilyakoz.saladbowl.cleancode.application.fragmetn_presentation.CreateRecipeViewModel
import com.ilyakoz.saladbowl.cleancode.application.fragmetn_presentation.WelcomeFragmentViewModel
import com.ilyakoz.saladbowl.cleancode.application.fragmetn_presentation.RecipeInfoFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    viewModel<WelcomeFragmentViewModel> {
        WelcomeFragmentViewModel(
            getListRecipeUseCase = get(),
            deleteRecipeUseCase = get()
        )
    }
    viewModel<CreateRecipeViewModel> {
        CreateRecipeViewModel(get())
    }
    viewModel<RecipeInfoFragmentViewModel> {
        RecipeInfoFragmentViewModel(get())
    }
    viewModel<EditRecipeViewModel>{
        EditRecipeViewModel(get(),get())
    }
}
