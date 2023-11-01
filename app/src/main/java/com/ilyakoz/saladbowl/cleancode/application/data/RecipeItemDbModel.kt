package com.ilyakoz.saladbowl.cleancode.application.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "recipe_items")
data class RecipeItemDbModel(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val ingredients: String?,
    val description: String?,
    val time: String?,
    val imageUri: String?
) : Parcelable
