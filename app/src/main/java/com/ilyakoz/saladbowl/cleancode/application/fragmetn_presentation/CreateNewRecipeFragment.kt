package com.ilyakoz.saladbowl.cleancode.application.fragmetn_presentation

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ilyakoz.saladbowl.R
import com.ilyakoz.saladbowl.databinding.FragmentCreateNewRecipeBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class CreateNewRecipeFragment : Fragment() {


    private var _binding: FragmentCreateNewRecipeBinding? = null
    private val binding: FragmentCreateNewRecipeBinding
        get() = _binding ?: throw RuntimeException("FragmentFragmentCreateNewRecipe is null")


    private val viewModel by viewModel<CreateRecipeViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateNewRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cancelButton.setOnClickListener {
            findNavController().navigate(R.id.welcomeFragment)
        }
        binding.AddImageViewBtn.setOnClickListener {
            openGalleryOrCamera()
        }
        binding.saveButton.setOnClickListener {
            launchAddMode()
        }
    }

    private fun launchAddMode() {
        val name = binding.titleFld.text.toString()
        val ingredients = binding.ingredientsFld?.text?.toString()
        val description = binding.descriptionFld?.text?.toString()
        val time = binding.timeFld?.text?.toString()

        if (name.isNotEmpty()) {
            lifecycleScope.launch {
                viewModel.addRecipeItem(
                    name,
                    ingredients,
                    description,
                    time,
                    viewModel.selectedImageUri.value?.toString()
                )
            }
            findNavController().navigate(R.id.welcomeFragment)
        } else {
            showToast(getString(R.string.input_text))
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG)
            .show()
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { result ->
            if (result != null) {
                viewModel.selectedImageUri.postValue(result)
                Glide.with(this).load(result).into(binding.saladImageView)
            }
        }

    private fun openGalleryOrCamera() {
        try {
            launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "Photo picker not available", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}