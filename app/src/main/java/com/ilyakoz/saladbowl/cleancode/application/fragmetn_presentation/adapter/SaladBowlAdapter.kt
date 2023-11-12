package com.ilyakoz.saladbowl.cleancode.application.fragmetn_presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ilyakoz.saladbowl.cleancode.application.domain.RecipeItem
import com.ilyakoz.saladbowl.databinding.ItemSaladBinding

class SaladBowlAdapter :
    ListAdapter<RecipeItem, RecipeItemViewHolder>(RecipeItemDiffCallback) {


    var onRecipeItemClickListener: ((RecipeItem) -> Unit)? = null
    var onRecipeItemLongClickListener: ((RecipeItem) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeItemViewHolder {
        val binding = ItemSaladBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecipeItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.itemView.setOnClickListener {
            onRecipeItemClickListener?.invoke(item)
        }
        holder.itemView.setOnLongClickListener {
            onRecipeItemLongClickListener?.invoke(item)
            true

        }

    }


}