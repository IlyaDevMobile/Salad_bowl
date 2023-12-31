package com.ilyakoz.saladbowl.cleancode.application.fragmetn_presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.ilyakoz.saladbowl.cleancode.application.domain.RecipeItem

object RecipeItemDiffCallback : DiffUtil.ItemCallback<RecipeItem>() {
    override fun areItemsTheSame(oldItem: RecipeItem, newItem: RecipeItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RecipeItem, newItem: RecipeItem): Boolean {
        return oldItem == newItem
    }
}