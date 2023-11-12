package com.ilyakoz.saladbowl.cleancode.application.di

import android.app.Application
import androidx.room.Room
import com.ilyakoz.saladbowl.cleancode.application.data.AppDatabase
import com.ilyakoz.saladbowl.cleancode.application.data.SaladBowlRepositoryImpl
import com.ilyakoz.saladbowl.cleancode.application.domain.SaladBowlRepository
import org.koin.core.scope.get
import org.koin.dsl.module




val databaseModule = module {
    single {
        Room.databaseBuilder(
            get<Application>(),
            AppDatabase::class.java,
            "recipe_item4.db"
        ).build()
    }
}




val dataModule = module {
    single { get<AppDatabase>().recipeItemDao() }

    single<SaladBowlRepository> {
        SaladBowlRepositoryImpl(recipeItemDao =  get())
    }


}

