package com.ilyakoz.saladbowl

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ilyakoz.saladbowl.cleancode.application.presentation.createNewRecipe.CreateRecipeActivity

class RecipeDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)
    }

    companion object {

        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val MODE_INFO = "mode_info"
        private const val EXTRA_RECIPE_ITEM_ID = "extra_recipe_item_id"


        fun newIntentShowDetailRecipe(context: Context,recipeItemId: Int): Intent {
            val intent = Intent(context, RecipeDetailActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_INFO)
            intent.putExtra(EXTRA_RECIPE_ITEM_ID, recipeItemId)

            return intent

        }
    }
}