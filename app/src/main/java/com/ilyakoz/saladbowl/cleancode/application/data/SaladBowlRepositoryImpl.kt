package com.ilyakoz.saladbowl.cleancode.application.data

import com.ilyakoz.saladbowl.cleancode.application.domain.RecipeItem
import com.ilyakoz.saladbowl.cleancode.application.domain.SaladBowlRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

class SaladBowlRepositoryImpl(private val recipeItemDao: RecipeItemDao) : SaladBowlRepository {

    private val mapper = SaladBowlMapper()




    override suspend fun addRecipe(recipeItem: RecipeItem) {
        val dbModel = mapper.mapEntityDbModel(recipeItem)
        recipeItemDao.addRecipe(dbModel)
    }


    override suspend fun deleteRecipe(recipeItem: RecipeItem) {
        recipeItemDao.deleteRecipe(recipeItem.id)
    }

    override suspend fun editRecipe(recipeItem: RecipeItem) {
        recipeItemDao.addRecipe(mapper.mapEntityDbModel(recipeItem))
    }

    override suspend fun getRecipe(recipeItemId: Int): RecipeItem {
        val dbModel = recipeItemDao.getRecipe(recipeItemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getRecipeList(): Flow<List<RecipeItem>> = recipeItemDao.getRecipeList().map { listDb->
        listDb.map { mapper.mapDbModelToEntity(it) }
    }

}




