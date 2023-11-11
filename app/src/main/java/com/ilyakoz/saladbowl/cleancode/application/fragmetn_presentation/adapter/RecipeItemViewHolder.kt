package com.ilyakoz.saladbowl.cleancode.application.fragmetn_presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ilyakoz.saladbowl.R
import com.ilyakoz.saladbowl.databinding.ItemSaladBinding
import com.ilyakoz.saladbowl.cleancode.application.domain.RecipeItem

class RecipeItemViewHolder(private val binding: ItemSaladBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: RecipeItem) {
        binding.saladNameTv.text = item.name
        Glide.with(itemView)
            .load(item.imageUri)
            .error(R.drawable.empty_photo)
            .into(binding.saladImageview)
    }


}