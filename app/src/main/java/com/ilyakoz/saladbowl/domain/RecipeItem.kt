package com.ilyakoz.saladbowl.domain

data class RecipeItem(

    val name: String,
    val ingredients: String,
    val image: String?,
    val timeStamp: Long,
    val id: Int = UNDEFINED_ID

    )

{
    companion object{
        const val UNDEFINED_ID = 0
    }
}
