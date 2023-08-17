package com.ilyakoz.saladbowl.cleancode.application.data

import android.app.Application
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.bumptech.glide.Glide
import com.ilyakoz.saladbowl.cleancode.application.domain.RecipeItem
import com.ilyakoz.saladbowl.cleancode.application.domain.SaladBowlRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class SaladBowlRepositoryImpl(application: Application) : SaladBowlRepository {

    private val applicationContext = application.applicationContext
    private val recipeItemDao = AppDatabase.getInstance(application).recipeItemDao()
    private val mapper = SaladBowlMapper()

    override suspend fun addRecipe(recipeItem: RecipeItem, imageUri: Uri) {
        val imageFileName = saveImageToFile(imageUri)
        val dbModel = mapper.mapEntityDbModel(recipeItem, imageFileName)
        recipeItemDao.addRecipe(dbModel)
    }

    private suspend fun saveImageToFile(imageUri: Uri): String {
        return withContext(Dispatchers.IO) {
            val targetFile =
                File(applicationContext.cacheDir, "images/${System.currentTimeMillis()}.jpg")

            try {
                val downloadedFile = Glide.with(applicationContext)
                    .downloadOnly()
                    .load(imageUri)
                    .submit()
                    .get()

                val inputStream = FileInputStream(downloadedFile)
                val outputStream = FileOutputStream(targetFile)
                inputStream.use { input ->
                    outputStream.use { output ->
                        input.copyTo(output)
                    }
                }
            } catch (e: Exception) {
                // Обработайте исключение, если что-то пойдет не так при загрузке изображения
            }

            return@withContext targetFile.absolutePath
        }
    }

    override suspend fun deleteRecipe(recipeItem: RecipeItem) {
        recipeItemDao.deleteRecipe(recipeItem.id)
    }

    override suspend fun editRecipe(recipeItem: RecipeItem) {
        recipeItemDao.addRecipe(mapper.mapEntityDbModel(recipeItem, image = ""))
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

