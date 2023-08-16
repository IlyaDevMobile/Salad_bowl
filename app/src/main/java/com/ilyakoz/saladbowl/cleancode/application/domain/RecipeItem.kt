package com.ilyakoz.saladbowl.cleancode.application.domain

data class RecipeItem(

    val name: String,
    val ingredients: String,
    val image: String?,
    val description: String?,
    var id: Int = UNDEFINED_ID

    )

{
    companion object{
        const val UNDEFINED_ID = 0
    }
}
