package com.ilyakoz.saladbowl.cleancode.application.fragmetn_presentation

import android.content.ActivityNotFoundException
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ilyakoz.saladbowl.R
import com.ilyakoz.saladbowl.cleancode.application.domain.RecipeItem
import com.ilyakoz.saladbowl.cleancode.application.presentation.createNewRecipe.CreateRecipeActivity

import com.ilyakoz.saladbowl.cleancode.application.presentation.createNewRecipe.CreateRecipeViewModel
import com.ilyakoz.saladbowl.databinding.FragmentCreateNewRecipeBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class CreateNewRecipeFragment : Fragment() {




    private var _binding : FragmentCreateNewRecipeBinding? = null
    private val binding: FragmentCreateNewRecipeBinding
        get() = _binding ?: throw RuntimeException("FragmentFragmentCreateNewRecipe is null")


    private val viewModel by viewModel<CreateRecipeViewModel>()





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("CreateNewRecipeFragment","Мы тут")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateNewRecipeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launchAddMode()
        checkErrorName()
        setupTextWatcher()
        closeCancelActivity()
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            closeCancelActivity()
        }
        binding.AddImageViewBtn.setOnClickListener {
            openGalleryOrCamera()
        }
    }

    private fun launchAddMode() {
        binding.saveButton.setOnClickListener {
            with(binding) {
                val name = titleFld?.text?.toString()
                val ingredients = ingredientsFld?.text?.toString()
                val description = descriptionFld?.text?.toString()
                val time = timeFld?.text?.toString()

                viewModel.viewModelScope.launch {
                    viewModel.addRecipeItem(
                        name,
                        ingredients,
                        description,
                        time,
                        viewModel.selectedImageUri.value?.toString()
                    )
                }
                findNavController().navigate(R.id.welcomeFragment)

            }
        }
    }

    private fun checkErrorName() {
        viewModel.errorInputName.observe(viewLifecycleOwner) {
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
            findNavController().navigate(R.id.welcomeFragment)
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

    private val launcher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { result ->
        if (result != null) {
            viewModel.selectedImageUri.postValue(result)
            Glide.with(this).load(result).into(binding.saladImageView)
        }
    }

    private fun openGalleryOrCamera() {
        try {
            launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "Photo picker not available", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadAndDisplayImage(imageUri: Uri) {
        Glide.with(this)
            .load(imageUri)
            .into(binding.saladImageView)
    }
}