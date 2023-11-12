package com.ilyakoz.saladbowl.cleancode.application.di

import com.ilyakoz.saladbowl.cleancode.application.domain.AddRecipeUseCase
import com.ilyakoz.saladbowl.cleancode.application.domain.DeleteRecipeUseCase
import com.ilyakoz.saladbowl.cleancode.application.domain.EditRecipeUseCase
import com.ilyakoz.saladbowl.cleancode.application.domain.GetListRecipeUseCase
import com.ilyakoz.saladbowl.cleancode.application.domain.GetRecipeItemUseCase
import org.koin.dsl.module


val domainModule = module {

    factory<AddRecipeUseCase> {
        AddRecipeUseCase(saladBowlRepository = get())
    }
    factory<DeleteRecipeUseCase> {
        DeleteRecipeUseCase(saladBowlRepository = get())
    }
    factory<EditRecipeUseCase> {
        EditRecipeUseCase(saladBowlRepository = get())
    }
    factory<GetListRecipeUseCase> {
        GetListRecipeUseCase(saladBowlRepository = get())
    }
    factory<GetRecipeItemUseCase> {
        GetRecipeItemUseCase(saladBowlRepository = get())
    }

}