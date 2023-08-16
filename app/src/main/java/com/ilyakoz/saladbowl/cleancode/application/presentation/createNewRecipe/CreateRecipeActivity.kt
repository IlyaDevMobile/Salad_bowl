package com.ilyakoz.saladbowl.cleancode.application.presentation.createNewRecipe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import com.ilyakoz.saladbowl.R
import com.ilyakoz.saladbowl.cleancode.application.domain.RecipeItem
import com.ilyakoz.saladbowl.databinding.ActivityCreateRecipeBinding
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateRecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateRecipeBinding
    private val viewModel: CreateRecipeViewModel by viewModels()

    private var screenMode = MODE_UNKNOWN
    private var recipeItemId = RecipeItem.UNDEFINED_ID
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateRecipeBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        parseIntent()

        binding.titleFld.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }

        )

        binding.cancelButton.setOnClickListener {
            finish()
        }


        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
        viewModel.errorInputName.observe(this) {
            val message = if (it) {
                getString(R.string.error_input_count)
            } else {
                null

            }
            binding.titleFld.error = message
        }
        viewModel.shouldCloseScreen.observe(this) {
            finish()
        }
    }

    private fun launchAddMode() {
        binding.saveButton.setOnClickListener {
            with(binding) {
                val name = titleFld?.text?.toString()
                val ingredients = ingredientsFld?.text?.toString()
                val image = saladImageview?.contentDescription?.toString() // Change this logic
                val description = descriptionFld?.text?.toString()

                viewModel.viewModelScope.launch {
                    viewModel.addRecipeItem(name, ingredients, image, description)
                }
            }
        }
    }

    private fun launchEditMode() {
        viewModel.viewModelScope.launch {
            viewModel.getRecipeItem(recipeItemId)
            viewModel.recipeItem.observe(this@CreateRecipeActivity) { recipeItem ->
                binding.titleFld.setText(recipeItem.name)
                binding.ingredientsFld.setText(recipeItem.ingredients)
                binding.descriptionFld.setText(recipeItem.description)

                setupEditButton()
            }
        }
    }

    private fun setupEditButton() {
        binding.saveButton.setOnClickListener {
            with(binding) {
                val name = titleFld?.text?.toString()
                val ingredients = ingredientsFld?.text?.toString()
                val image = saladImageview?.contentDescription?.toString() // Change this logic
                val description = descriptionFld?.text?.toString()

                viewModel.viewModelScope.launch {
                    viewModel.editRecipeItem(name, ingredients, image, description)
                }
            }
        }
    }


    private fun parseIntent() {
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
            ?: throw IllegalArgumentException("Param screen mode is absent")
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw IllegalArgumentException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            recipeItemId = intent.getIntExtra(EXTRA_RECIPE_ITEM_ID, RecipeItem.UNDEFINED_ID)
            if (recipeItemId == RecipeItem.UNDEFINED_ID) {
                throw IllegalArgumentException("Param recipe item id is absent")
            }
        }
    }


    companion object {

        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_RECIPE_ITEM_ID = "extra_recipe_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, CreateRecipeActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, recipeItemId: Int): Intent {
            val intent = Intent(context, CreateRecipeActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_RECIPE_ITEM_ID, recipeItemId)
            return intent
        }

    }
}