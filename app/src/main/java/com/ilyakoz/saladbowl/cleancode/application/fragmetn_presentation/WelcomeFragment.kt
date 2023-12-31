package com.ilyakoz.saladbowl.cleancode.application.fragmetn_presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilyakoz.saladbowl.R
import com.ilyakoz.saladbowl.cleancode.application.data.RecipeItemDbModel
import com.ilyakoz.saladbowl.cleancode.application.domain.RecipeItem
import com.ilyakoz.saladbowl.cleancode.application.fragmetn_presentation.adapter.SaladBowlAdapter
import com.ilyakoz.saladbowl.databinding.FragmentWelcomeBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding: FragmentWelcomeBinding
        get() = _binding ?: throw RuntimeException("FragmentWelcomeBinding == null")

    private lateinit var adapter: SaladBowlAdapter

    private val viewModel by viewModel<WelcomeFragmentViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        lifecycleScope.launch {
            viewModel.recipeListTest.collect {
                adapter.submitList(it)
            }
        }
        binding.addNewRecipeBtn.setOnClickListener {
            findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToCreateNewRecipeFragment())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupRecyclerView() {
        val layout = GridLayoutManager(requireContext(), 2)
        binding.recyclerview.layoutManager = layout
        adapter = SaladBowlAdapter()
        binding.recyclerview.adapter = adapter
        adapter.onRecipeItemClickListener = {
            getRecipeItem(it)
        }
        deleteRecipeItem()


    }

    private fun deleteRecipeItem() {
        adapter.onRecipeItemLongClickListener = { recipeItem ->
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setTitle(getString(R.string.delete_recipe))
            alertDialogBuilder.setMessage(getString(R.string.exactly))
            alertDialogBuilder.setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                viewModel.viewModelScope.launch {
                    viewModel.deleteRecipeItem(recipeItem)
                }
                dialog.dismiss()
            }
            alertDialogBuilder.setNegativeButton(getString(R.string.no)) { dialog, _ ->
                dialog.dismiss()
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()

        }
    }


    private fun getRecipeItem(recipeItem: RecipeItem) {
        findNavController().navigate(
            WelcomeFragmentDirections.actionWelcomeFragmentToRecipeInfoFragment(
                recipeItem.id
            )
        )
    }
}



