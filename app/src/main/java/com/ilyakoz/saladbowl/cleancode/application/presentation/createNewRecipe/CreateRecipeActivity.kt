package com.ilyakoz.saladbowl.cleancode.application.presentation.createNewRecipe

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.ilyakoz.saladbowl.R
import com.ilyakoz.saladbowl.cleancode.application.domain.RecipeItem
import com.ilyakoz.saladbowl.databinding.ActivityCreateRecipeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateRecipeActivity : AppCompatActivity() {

    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>


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
        setupGalleryLauncher()
        setupAddImageViewBtn()
        viewModel.shouldCloseScreen.observe(this) {
            finish()
        }
    }

    private fun loadAndDisplayImage(imageUri: Uri) {
        Glide.with(this)
            .asBitmap()
            .load(imageUri)
            .centerCrop()
            .into(object : BitmapImageViewTarget(binding.saladImageview) {
                override fun setResource(resource: Bitmap?) {
                    binding.saladImageview.setImageBitmap(resource)
                }
            })
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
                val image = viewModel.selectedImageUri.value?.toString()


                viewModel.viewModelScope.launch {
                    viewModel.addRecipeItem(name, ingredients, description, image)
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

                // Загрузите и отобразите изображение, если оно есть в объекте recipeItem
                recipeItem.imageUri?.let { imageUriString ->
                    val imageUri = Uri.parse(imageUriString)
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
                val image = viewModel.selectedImageUri.value?.toString() // Получите ссылку на изображение

                viewModel.viewModelScope.launch {
                    viewModel.editRecipeItem(
                        name,
                        ingredients,
                        description,
                        image
                    ) // Передайте параметр image
                }
            }
        }
    }


    private fun setupGalleryLauncher() {
        galleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val selectedImageUri = result.data?.data
                    selectedImageUri?.let {
                        loadAndDisplayImage(it)
                    }
                }
            }
    }

    private fun setupAddImageViewBtn() {
        binding.AddImageViewBtn.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK)
            galleryIntent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            galleryLauncher.launch(galleryIntent)
        }
        // Set the selectedImageUri to the ViewModel's LiveData
        galleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val selectedImageUri = result.data?.data
                    selectedImageUri?.let {
                        viewModel.setSelectedImageUri(it) // Update the ViewModel's selectedImageUri
                        loadAndDisplayImage(it)
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