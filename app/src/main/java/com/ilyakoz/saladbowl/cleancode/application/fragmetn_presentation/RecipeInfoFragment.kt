package com.ilyakoz.saladbowl.cleancode.application.fragmetn_presentation

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ilyakoz.saladbowl.R
import com.ilyakoz.saladbowl.databinding.FragmentRecipeInfoBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class RecipeInfoFragment : Fragment() {


    private var _binding: FragmentRecipeInfoBinding? = null
    private val binding: FragmentRecipeInfoBinding
        get() = _binding ?: throw RuntimeException("FragmentRecipeInfo is null")


    private val viewModel by viewModel<RecipeInfoFragmentViewModel>()


    private val args by navArgs<RecipeInfoFragmentArgs>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecipeInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchInfoMode()
        binding.cancelButton.setOnClickListener {
            findNavController().navigate(R.id.welcomeFragment)
        }
        binding.editButton.setOnClickListener {
            findNavController().navigate(RecipeInfoFragmentDirections.actionRecipeInfoFragmentToEditRecipeFragment(args.recipeItem))
        }
    }


    private fun launchInfoMode() {
        viewModel.viewModelScope.launch {
            viewModel.getRecipeItem(args.recipeItem)
            viewModel.recipeItem.observe(viewLifecycleOwner) { recipeItem ->
                binding.titleTextView.text = recipeItem.name
                binding.ingredientsTextView.text = recipeItem.ingredients
                binding.descriptionTextView.text = recipeItem.description
                recipeItem.imageUri?.let {
                    val imageUri = Uri.parse(it)
                    loadAndDisplayImage(imageUri)
                }
                binding.TimeTextView.text = recipeItem.time
            }
        }
    }


    private fun loadAndDisplayImage(imageUri: Uri) {
        Glide.with(this)
            .load(imageUri)
            .into(binding.saladImageView2)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}