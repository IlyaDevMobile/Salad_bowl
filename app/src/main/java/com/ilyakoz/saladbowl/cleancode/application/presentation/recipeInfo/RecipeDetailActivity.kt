package com.ilyakoz.saladbowl.cleancode.application.presentation.recipeInfo

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.ilyakoz.saladbowl.cleancode.application.domain.RecipeItem
import com.ilyakoz.saladbowl.cleancode.application.presentation.createNewRecipe.CreateRecipeActivity
import com.ilyakoz.saladbowl.databinding.ActivityRecipeDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailBinding

    private val viewModel: RecipeDetailViewModel by viewModels()


    private var screenMode = MODE_UNKNOWN
    private var recipeItemId = RecipeItem.UNDEFINED_ID


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        parseIntent()
        closeCancelActivity()
        launchInfoMode()
        openEditActivity()



    }


    private fun parseIntent() {
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
            ?: throw IllegalArgumentException("Param screen mode is absent")
        if (mode != MODE_INFO) {
            throw IllegalArgumentException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_INFO) {
            recipeItemId = intent.getIntExtra(EXTRA_RECIPE_ITEM_ID, RecipeItem.UNDEFINED_ID)
            if (recipeItemId == RecipeItem.UNDEFINED_ID) {
                throw IllegalArgumentException("Param recipe item id is absent")

            }
        }
    }


    private fun openEditActivity() {
        binding.editButton.setOnClickListener {
            val intent = CreateRecipeActivity.newIntentEditItem(this, recipeItemId)
            startActivity(intent)
        }
    }

    private fun launchInfoMode() {
        viewModel.viewModelScope.launch {
            viewModel.getRecipeItem(recipeItemId)
            viewModel.recipeItem.observe(this@RecipeDetailActivity) { recipeItem ->
                binding.titleTextView.text = recipeItem.name
                binding.ingredientsTextView.text = recipeItem.ingredients
                binding.descriptionTextView.text = recipeItem.description
                recipeItem.imageUri?.let { imageUriString ->
                    val imageUri = Uri.parse(imageUriString)
                    Log.d("ImageUriDebug", "Loaded image URI: $imageUri")
                    loadAndDisplayImage(imageUri)
                }
            }
        }
    }


    private fun loadAndDisplayImage(imageUri: Uri) {
        Glide.with(this)
            .load(imageUri)
            .into(binding.saladImageView2)
    }

    private fun closeCancelActivity() {
        binding.cancelButton.setOnClickListener {
            finish()
        }
    }


    companion object {

        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val MODE_INFO = "mode_info"
        private const val MODE_UNKNOWN = ""
        private const val EXTRA_RECIPE_ITEM_ID = "extra_recipe_item_id"


        fun newIntentShowDetailRecipe(context: Context, recipeItemId: Int): Intent {
            val intent = Intent(context, RecipeDetailActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_INFO)
            intent.putExtra(EXTRA_RECIPE_ITEM_ID, recipeItemId)

            return intent

        }
    }
}