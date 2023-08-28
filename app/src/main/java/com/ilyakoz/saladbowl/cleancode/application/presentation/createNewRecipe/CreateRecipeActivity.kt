package com.ilyakoz.saladbowl.cleancode.application.presentation.createNewRecipe

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.ilyakoz.saladbowl.R
import com.ilyakoz.saladbowl.cleancode.application.domain.RecipeItem
import com.ilyakoz.saladbowl.cleancode.application.presentation.listRecipe.MainActivity
import com.ilyakoz.saladbowl.databinding.ActivityCreateRecipeBinding
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
        setupTextWatcher()
        closeCancelActivity()
        selectModeActivity()
        checkErrorName()
        viewModel.shouldCloseScreen.observe(this) {
            finish()
        }
        binding.AddImageViewBtn.setOnClickListener {
            openGalleryOrCamera()
        }
    }


    private fun checkErrorName() {
        viewModel.errorInputName.observe(this) {
            val message = if (it) {
                getString(R.string.error_input_count)
            } else {
                null

            }
            binding.titleFld.error = message
        }
    }

    private fun closeCancelActivity() {
        binding.cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun setupTextWatcher() {
        binding.titleFld.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun selectModeActivity() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun launchAddMode() {
        binding.saveButton.setOnClickListener {
            with(binding) {
                val name = titleFld?.text?.toString()
                val ingredients = ingredientsFld?.text?.toString()
                val description = descriptionFld?.text?.toString()


                viewModel.viewModelScope.launch {
                    viewModel.addRecipeItem(
                        name,
                        ingredients,
                        description,
                        viewModel.selectedImageUri.value?.toString()
                    )
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

                recipeItem.imageUri?.let { imageUriString ->
                    val imageUri = Uri.parse(imageUriString)
                    Log.d("ImageUriDebug", "Loaded image URI: $imageUri")
                    loadAndDisplayImage(imageUri)
                }

                setupEditButton()
            }
        }
    }


    private fun setupEditButton() {
        binding.saveButton.setOnClickListener {
            with(binding) {
                val name = titleFld?.text?.toString()
                val ingredients = ingredientsFld?.text?.toString()
                val description = descriptionFld?.text?.toString()
                val imageUri = viewModel.selectedImageUri.value?.toString()

                viewModel.viewModelScope.launch {
                    viewModel.editRecipeItem(
                        name,
                        ingredients,
                        description,
                        imageUri ?: viewModel.recipeItem.value?.imageUri
                    )

                    val intent = MainActivity.newIntentListRecipeActivity(this@CreateRecipeActivity)
                    startActivity(intent)
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

    private val launcher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { result ->
            if (result != null) {
                viewModel.selectedImageUri.postValue(result)  // Устанавливаем значение в ViewModel
                Glide.with(this).load(result).into(binding.saladImageView)
            }
        }

    private fun openGalleryOrCamera() {
        try {
            launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        } catch (e: ActivityNotFoundException) {
            // Handle the case where the picker is not available
            Toast.makeText(this, "Photo picker not available", Toast.LENGTH_SHORT).show()
        }
    }


    private fun loadAndDisplayImage(imageUri: Uri) {
        Glide.with(this)
            .load(imageUri)
            .into(binding.saladImageView)
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