package com.ilyakoz.saladbowl.cleancode.application.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RecipeItemDbModel::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun recipeItemDao(): RecipeItemDao

}