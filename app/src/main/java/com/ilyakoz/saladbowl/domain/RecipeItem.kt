package com.ilyakoz.saladbowl.domain

import android.accounts.AuthenticatorDescription

data class RecipeItem(

    val name: String,
    val ingredients: String,
    val image: String?,
    val description: String?,
    val id: Int = UNDEFINED_ID

    )

{
    companion object{
        const val UNDEFINED_ID = 0
    }
}
