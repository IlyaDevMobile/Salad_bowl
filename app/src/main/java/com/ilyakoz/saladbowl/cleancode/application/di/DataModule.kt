package com.ilyakoz.saladbowl.cleancode.application.di

import com.ilyakoz.saladbowl.cleancode.application.data.AppDatabase
import com.ilyakoz.saladbowl.cleancode.application.data.RecipeItemDao
import com.ilyakoz.saladbowl.cleancode.application.data.SaladBowlRepositoryImpl
import com.ilyakoz.saladbowl.cleancode.application.domain.SaladBowlRepository
import org.koin.dsl.module


//@Module
//@InstallIn(SingletonComponent::class)
//class DataModule {
//
//    @Provides
//    @Singleton
//    fun provideRecipeItemDao(appDatabase: AppDatabase) : RecipeItemDao{
//        return appDatabase.recipeItemDao()
//    }
//
//    @Provides
//    @Singleton
//    fun provideSaladBowlRepository(application: Application) : SaladBowlRepository{
//        return SaladBowlRepositoryImpl(application)
//    }
//}


val dataModule = module {
    single { get<AppDatabase>().recipeItemDao() }

    single<SaladBowlRepository> {
        SaladBowlRepositoryImpl(get())
    }


}

