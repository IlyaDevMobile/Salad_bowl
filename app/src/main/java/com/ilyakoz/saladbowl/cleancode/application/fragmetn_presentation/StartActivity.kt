package com.ilyakoz.saladbowl.cleancode.application.fragmetn_presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ilyakoz.saladbowl.R
import com.ilyakoz.saladbowl.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityStartBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val navController = findNavController(R.id.fragmentContainerView)

        binding.bottomNavigationView.setupWithNavController(navController)



    }
}