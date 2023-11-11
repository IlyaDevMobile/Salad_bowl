package com.ilyakoz.saladbowl.cleancode.application.di

import com.ilyakoz.saladbowl.cleancode.application.data.AppDatabase
import com.ilyakoz.saladbowl.cleancode.application.data.SaladBowlRepositoryImpl
import com.ilyakoz.saladbowl.cleancode.application.domain.SaladBowlRepository
import org.koin.dsl.module







val dataModule = module {
    single { get<AppDatabase>().recipeItemDao() }

    single<SaladBowlRepository> {
        SaladBowlRepositoryImpl(get())
    }


}

