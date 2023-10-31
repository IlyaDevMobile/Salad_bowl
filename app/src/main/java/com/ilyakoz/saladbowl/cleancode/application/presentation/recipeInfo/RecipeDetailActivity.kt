package com.ilyakoz.saladbowl.cleancode.application.presentation.recipeInfo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.ilyakoz.saladbowl.R
import com.ilyakoz.saladbowl.cleancode.application.domain.RecipeItem
import com.ilyakoz.saladbowl.databinding.ActivityRecipeDetailBinding


class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailBinding


    private var screenMode = MODE_UNKNOWN
    private var recipeItemId = RecipeItem.UNDEFINED_ID


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        parseIntent()
        if (savedInstanceState == null) {
            startFragment()
        }


    }

    private fun startFragment() {
        val fragment = when (screenMode) {
            MODE_INFO -> RecipeItemFragment.newInstanceInfoItem(recipeItemId)
            else -> throw RuntimeException("Unknown screen mode $screenMode")
        }
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.recipe_item_container,
                RecipeItemFragment.newInstanceInfoItem(recipeItemId)
            )
            .commit()
    }


    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw java.lang.RuntimeException("Param screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_INFO) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_INFO) {
            if (!intent.hasExtra(EXTRA_RECIPE_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            recipeItemId = intent.getIntExtra(EXTRA_RECIPE_ITEM_ID, RecipeItem.UNDEFINED_ID)
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