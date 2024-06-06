package com.example.pmf.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.pmf.DB.DBHelper
import com.example.pmf.databinding.FragmentRecipeBinding
import com.example.pmf.ui.recipe.RecipeViewModel
import com.example.pmf.ui.recipe.RecipeViewModelFactory

class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dbHelper = DBHelper(requireContext())
        val recipeViewModelFactory = RecipeViewModelFactory(dbHelper)
        val recipeViewModel = ViewModelProvider(this, recipeViewModelFactory).get(RecipeViewModel::class.java)

        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textRecipe
        recipeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val buttonRecommend: Button = binding.buttonRecommend
        buttonRecommend.setOnClickListener {
            recipeViewModel.recommendRecipe()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
