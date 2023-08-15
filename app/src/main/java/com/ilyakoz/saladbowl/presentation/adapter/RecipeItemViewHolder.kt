package com.ilyakoz.saladbowl.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.ilyakoz.saladbowl.R
import com.ilyakoz.saladbowl.databinding.ItemSaladBinding
import com.ilyakoz.saladbowl.domain.RecipeItem

class RecipeItemViewHolder(private val binding: ItemSaladBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: RecipeItem) {
        binding.saladNameTv.text = item.name
        binding.saladImageview.setImageResource(R.drawable.salad)
    }


}