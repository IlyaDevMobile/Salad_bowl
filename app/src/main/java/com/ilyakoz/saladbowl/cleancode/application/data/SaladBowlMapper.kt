package com.ilyakoz.saladbowl.cleancode.application.data

import com.ilyakoz.saladbowl.cleancode.application.domain.RecipeItem

class SaladBowlMapper {

    fun mapEntityDbModel(recipeItem: RecipeItem) = RecipeItemDbModel(
        id = recipeItem.id,
        name = recipeItem.name,
        ingredients = recipeItem.ingredients,
        description = recipeItem.description,
        time = recipeItem.time,
        imageUri = recipeItem.imageUri,
    )

    fun mapDbModelToEntity(recipeItemDbModel: RecipeItemDbModel) = RecipeItem(
        id = recipeItemDbModel.id,
        name = recipeItemDbModel.name,
        ingredients = recipeItemDbModel.ingredients,
        description = recipeItemDbModel.description,
        time = recipeItemDbModel.time,
        imageUri = recipeItemDbModel.imageUri,
    )

    fun mapListDbModelToListEntity(list: List<RecipeItemDbModel>) = list.map {
        mapDbModelToEntity(it)
    }
}
