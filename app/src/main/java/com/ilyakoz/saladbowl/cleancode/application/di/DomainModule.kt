package com.ilyakoz.saladbowl.cleancode.application.di

import com.ilyakoz.saladbowl.cleancode.application.domain.AddRecipeUseCase
import com.ilyakoz.saladbowl.cleancode.application.domain.DeleteRecipeUseCase
import com.ilyakoz.saladbowl.cleancode.application.domain.EditRecipeUseCase
import com.ilyakoz.saladbowl.cleancode.application.domain.GetListRecipeUseCase
import com.ilyakoz.saladbowl.cleancode.application.domain.GetRecipeItemUseCase
import com.ilyakoz.saladbowl.cleancode.application.domain.SaladBowlRepository
import org.koin.dsl.module


//@Module
//@InstallIn(ViewModelComponent::class)
//class DomainModule {
//
//
//    @Provides
//    fun provideAddRecipeUseCase(saladBowlRepository: SaladBowlRepository): AddRecipeUseCase {
//        return AddRecipeUseCase(saladBowlRepository)
//    }
//
//    @Provides
//    fun provideDeleteRecipeUseCase(saladBowlRepository: SaladBowlRepository): DeleteRecipeUseCase {
//        return DeleteRecipeUseCase(saladBowlRepository)
//    }
//
//    @Provides
//    fun provideEditRecipeUseCase(saladBowlRepository: SaladBowlRepository): EditRecipeUseCase {
//        return EditRecipeUseCase(saladBowlRepository)
//    }
//
//    @Provides
//    fun provideGetListRecipeUseCase(saladBowlRepository: SaladBowlRepository): GetListRecipeUseCase {
//        return GetListRecipeUseCase(saladBowlRepository)
//    }
//
//    @Provides
//    fun provideGetRecipeItemUseCase(saladBowlRepository: SaladBowlRepository): GetRecipeItemUseCase {
//        return GetRecipeItemUseCase(saladBowlRepository)
//    }
//
//
//}

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