package com.ilyakoz.saladbowl.cleancode.application.fragmetn_presentation

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ilyakoz.saladbowl.R
import com.ilyakoz.saladbowl.databinding.FragmentEditNewRecipeBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class EditRecipeFragment : Fragment() {

    private var _binding: FragmentEditNewRecipeBinding? = null
    private val binding: FragmentEditNewRecipeBinding
        get() = _binding ?: throw RuntimeException("FragmentEditNewRecipe is null")

    private val viewModel by viewModel<EditRecipeViewModel>()


    private val args by navArgs<EditRecipeFragmentArgs>()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditNewRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchEditMode()
        binding.cancelButton.setOnClickListener {
            findNavController().navigate(R.id.welcomeFragment)
        }

    }

    private fun launchEditMode() {
        viewModel.viewModelScope.launch {
            viewModel.getRecipeItem(args.recipeEdit)
            viewModel.recipeItem.observe(viewLifecycleOwner) { recipeItem ->
                binding.titleFld.setText(recipeItem.name)
                binding.ingredientsFld.setText(recipeItem.ingredients)
                binding.descriptionFld.setText(recipeItem.description)
                binding.timeFld.setText(recipeItem.time)


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
                val time = timeFld?.text?.toString()
                val imageUri = viewModel.selectedImageUri.value?.toString()

                viewModel.viewModelScope.launch {
                    viewModel.editRecipeItem(
                        name,
                        ingredients,
                        description,
                        time,
                        imageUri ?: viewModel.recipeItem.value?.imageUri
                    )
                    findNavController().navigate(R.id.welcomeFragment)

                }
            }
        }
    }

    private fun loadAndDisplayImage(imageUri: Uri) {
        Glide.with(this)
            .load(imageUri)
            .into(binding.saladImageView)
    }
}






















