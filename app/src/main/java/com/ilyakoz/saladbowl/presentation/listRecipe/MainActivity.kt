package com.ilyakoz.saladbowl.presentation.listRecipe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import com.ilyakoz.saladbowl.databinding.ActivityMainBinding
import com.ilyakoz.saladbowl.presentation.adapter.SaladBowlAdapter
import com.ilyakoz.saladbowl.presentation.createNewRecipe.CreateRecipeActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: SaladBowlAdapter

    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        val view = binding.root
        setContentView(view)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setupRecyclerView()
        viewModel.recipeListTest.observe(this) {
            adapter.submitList(it)
        }


        binding.searchIcon.setOnClickListener {
        }


        binding.addNewRecipeBtn.setOnClickListener {
            Log.d("MainActivity", it.toString())
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
            Log.d("MainActivity", it.toString())
            val intent = CreateRecipeActivity.newIntentEditItem(this, it.id)
            startActivity(intent)
        }
    }
}