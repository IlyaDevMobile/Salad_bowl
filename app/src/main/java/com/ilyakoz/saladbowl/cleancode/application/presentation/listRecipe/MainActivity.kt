package com.ilyakoz.saladbowl.cleancode.application.presentation.listRecipe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import com.ilyakoz.saladbowl.cleancode.application.presentation.adapter.SaladBowlAdapter
import com.ilyakoz.saladbowl.cleancode.application.presentation.createNewRecipe.CreateRecipeActivity
import com.ilyakoz.saladbowl.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: SaladBowlAdapter

    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        val view = binding.root
        setContentView(view)
        setupRecyclerView()
        viewModel.recipeListTest.observe(this) {
            adapter.submitList(it)
        }


        binding.searchIcon.setOnClickListener {
        }


        binding.addNewRecipeBtn.setOnClickListener {
            val intent = CreateRecipeActivity.newIntentAddItem(this)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        val layout = GridLayoutManager(this, 2)
        binding.recyclerview.layoutManager = layout
        adapter = SaladBowlAdapter()
        binding.recyclerview.adapter = adapter
        getRecipeItem()
        deleteRecipeItem()


    }

    private fun deleteRecipeItem() {
        adapter.onRecipeItemLongClickListener = { recipeItem ->
            viewModel.viewModelScope.launch {
                viewModel.deleteRecipeItem(recipeItem)
            }
        }
    }

    private fun getRecipeItem() {
        adapter.onRecipeItemClickListener = {
            val intent = CreateRecipeActivity.newIntentEditItem(this, it.id)
            startActivity(intent)
        }
    }
}