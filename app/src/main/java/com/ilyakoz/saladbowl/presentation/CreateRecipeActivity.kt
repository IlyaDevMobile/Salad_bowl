package com.ilyakoz.saladbowl.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ilyakoz.saladbowl.R

class CreateRecipeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_recipe)
    }

    companion object{
        fun createIntent(context: Context) : Intent{
            return Intent(context,CreateRecipeActivity::class.java)
        }
    }
}