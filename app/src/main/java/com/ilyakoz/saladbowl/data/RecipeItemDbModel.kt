package com.ilyakoz.saladbowl.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "recipe_item")
data class RecipeItemDbModel(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val ingredients: String,
    val image: String?,
    val timeStamp: Long

)
