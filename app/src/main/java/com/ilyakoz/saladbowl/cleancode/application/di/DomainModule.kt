package com.ilyakoz.saladbowl.cleancode.application.di

import com.ilyakoz.saladbowl.cleancode.application.domain.AddRecipeUseCase
import com.ilyakoz.saladbowl.cleancode.application.domain.DeleteRecipeUseCase
import com.ilyakoz.saladbowl.cleancode.application.domain.EditRecipeUseCase
import com.ilyakoz.saladbowl.cleancode.application.domain.GetListRecipeUseCase
import com.ilyakoz.saladbowl.cleancode.application.domain.GetRecipeItemUseCase
import org.koin.dsl.module


val domainModule = module {

    factory<AddRecipeUseCase> {
        AddRecipeUseCase(get())
    }
    factory<DeleteRecipeUseCase> {
        DeleteRecipeUseCase(get())
    }
    factory<EditRecipeUseCase> {
        EditRecipeUseCase(get())
    }
    factory<GetListRecipeUseCase> {
        GetListRecipeUseCase(get())
    }
    factory<GetRecipeItemUseCase> {
        GetRecipeItemUseCase(get())
    }

}