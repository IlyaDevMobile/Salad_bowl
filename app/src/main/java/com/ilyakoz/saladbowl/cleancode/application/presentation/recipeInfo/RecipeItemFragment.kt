package com.ilyakoz.saladbowl.cleancode.application.presentation.recipeInfo

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.ilyakoz.saladbowl.cleancode.application.domain.RecipeItem
import com.ilyakoz.saladbowl.cleancode.application.presentation.createNewRecipe.CreateRecipeActivity
import com.ilyakoz.saladbowl.databinding.FragmentRecipeItemBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class RecipeItemFragment : Fragment() {


    private var _binding: FragmentRecipeItemBinding? = null
    private val binding: FragmentRecipeItemBinding
        get() = _binding ?: throw RuntimeException("FragmentRecipeItemBinding is null")


    private val viewModel: RecipeDetailViewModel by viewModels()


    private var screenMode = MODE_UNKNOWN
    private var recipeItemId = RecipeItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeItemBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        closeCancelActivity()
        launchInfoMode()
        openEditActivity()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = args.getString(EXTRA_SCREEN_MODE)
        if (mode != MODE_INFO) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == MODE_INFO) {
            if (!args.containsKey(EXTRA_RECIPE_ITEM_ID)) {
                throw RuntimeException("Param recipe item id is absent")

            }
            recipeItemId = args.getInt(EXTRA_RECIPE_ITEM_ID,RecipeItem.UNDEFINED_ID)
        }
    }


    private fun openEditActivity() {
        binding.editButton.setOnClickListener {
            val intent = CreateRecipeActivity.newIntentEditItem(requireActivity(), recipeItemId)
            startActivity(intent)
        }
    }

    private fun launchInfoMode() {
        viewModel.viewModelScope.launch {
            viewModel.getRecipeItem(recipeItemId)
            viewModel.recipeItem.observe(viewLifecycleOwner) { recipeItem ->
                binding.titleTextView.text = recipeItem.name
                binding.ingredientsTextView.text = recipeItem.ingredients
                binding.descriptionTextView.text = recipeItem.description
                recipeItem.imageUri?.let {
                    val imageUri = Uri.parse(it)
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
            activity?.onBackPressed()
        }
    }


    companion object {

        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val MODE_INFO = "mode_info"
        private const val MODE_UNKNOWN = ""
        private const val EXTRA_RECIPE_ITEM_ID = "extra_recipe_item_id"


        fun newInstanceInfoItem(recipeItemId: Int): RecipeItemFragment {
            return RecipeItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, MODE_INFO)
                    putInt(EXTRA_RECIPE_ITEM_ID, recipeItemId)
                }
            }

        }


    }
}
