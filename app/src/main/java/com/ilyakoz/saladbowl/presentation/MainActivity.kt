package com.ilyakoz.saladbowl.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import com.ilyakoz.saladbowl.R
import com.ilyakoz.saladbowl.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        val view = binding.root
        setContentView(view)

        binding.searchIcon.setOnClickListener {

        }


        binding.addNewRecipeBtn.setOnClickListener {
            val nextActivityCreateRecipeActivity = CreateRecipeActivity.createIntent(this)
            startActivity(nextActivityCreateRecipeActivity)
        }
    }
}