package com.ilyakoz.saladbowl.data

import com.ilyakoz.saladbowl.domain.RecipeItem

class SaladBowlMapper {

    fun mapEntityDbModel(recipeItem: RecipeItem) = RecipeItemDbModel(
        id = recipeItem.id,
        name = recipeItem.name,
        ingredients = recipeItem.ingredients,
        image = recipeItem.image,
        timeStamp = recipeItem.timeStamp
    )

    fun mapDbModelToEntity(recipeItemDbModel: RecipeItemDbModel) = RecipeItem(
        id = recipeItemDbModel.id,
        name = recipeItemDbModel.name,
        ingredients = recipeItemDbModel.ingredients,
        image = recipeItemDbModel.image,
        timeStamp = recipeItemDbModel.timeStamp
    )

    fun mapListDbModelToListEntity(list : List<RecipeItemDbModel>) = list.map {
        mapDbModelToEntity(it)
    }
}

