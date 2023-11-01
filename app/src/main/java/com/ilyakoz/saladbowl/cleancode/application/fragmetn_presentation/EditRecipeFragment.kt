package com.ilyakoz.saladbowl.cleancode.application.fragmetn_presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ilyakoz.saladbowl.R
import com.ilyakoz.saladbowl.databinding.FragmentEditNewRecipeBinding


class EditRecipeFragment : Fragment() {

    private var _binding : FragmentEditNewRecipeBinding? = null
    private val binding : FragmentEditNewRecipeBinding
        get() = _binding ?: throw RuntimeException("FragmentEditNewRecipe is null")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditNewRecipeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}






















