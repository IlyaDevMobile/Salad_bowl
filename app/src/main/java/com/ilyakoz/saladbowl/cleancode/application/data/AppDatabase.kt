package com.ilyakoz.saladbowl.cleancode.application.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


abstract class AppDatabase : RoomDatabase() {

    abstract fun recipeItemDao(): RecipeItemDao

}