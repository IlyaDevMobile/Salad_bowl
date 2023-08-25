package com.ilyakoz.saladbowl.cleancode.application.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.ilyakoz.saladbowl.cleancode.application.domain.RecipeItem
import com.ilyakoz.saladbowl.cleancode.application.domain.SaladBowlRepository

class SaladBowlRepositoryImpl(application: Application) : SaladBowlRepository {

    private val recipeItemDao = AppDatabase.getInstance(application).recipeItemDao()
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

    override fun getRecipeList(): LiveData<List<RecipeItem>> = recipeItemDao.getRecipeList()
        .map { list ->
            list.map { dbModel ->
                mapper.mapDbModelToEntity(dbModel)
            }
        }

}


