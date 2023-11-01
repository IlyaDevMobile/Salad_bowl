package com.ilyakoz.saladbowl.cleancode.application.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class RecipeItem(

    val name: String,
    val ingredients: String?,
    val description: String?,
    val time: String?,
    val imageUri: String?,
    var id: Int = UNDEFINED_ID

)
{
    companion object {
        const val UNDEFINED_ID = 0
    }
}
