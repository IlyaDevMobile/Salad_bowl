package com.ilyakoz.saladbowl.cleancode.application.presentation.listRecipe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import com.ilyakoz.saladbowl.cleancode.application.presentation.adapter.SaladBowlAdapter
import com.ilyakoz.saladbowl.cleancode.application.presentation.createNewRecipe.CreateRecipeActivity
import com.ilyakoz.saladbowl.cleancode.application.presentation.recipeInfo.RecipeDetailActivity
import com.ilyakoz.saladbowl.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: SaladBowlAdapter

    private val viewModel by viewModel<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        val view = binding.root
        setContentView(view)
        setupRecyclerView()
        viewModel.recipeListTest.observe(this) {
            adapter.submitList(it)
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
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Delete Recipe")
            alertDialogBuilder.setMessage("Are you sure you want to delete this recipe?")
            alertDialogBuilder.setPositiveButton("Yes") { dialog, _ ->
                viewModel.viewModelScope.launch {
                    viewModel.deleteRecipeItem(recipeItem)
                }
                dialog.dismiss()
            }
            alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()

        }
    }


    private fun getRecipeItem() {
        adapter.onRecipeItemClickListener = {
            val intent = RecipeDetailActivity.newIntentShowDetailRecipe(this, it.id)
            startActivity(intent)
        }
    }

    companion object {


        fun newIntentListRecipeActivity(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}