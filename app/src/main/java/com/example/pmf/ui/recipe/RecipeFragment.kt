package com.example.pmf.ui.recipe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pmf.DB.DBHelper
import com.example.pmf.databinding.FragmentRecipeBinding
import com.example.pmf.ui.recipe.RecipeViewModel
import com.example.pmf.ui.recipe.RecipeViewModelFactory
import com.example.pmf.ui.recipe.RecipesAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.example.pmf.ui.recipe.Recipe



class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding!!

    private lateinit var recipesAdapter: RecipesAdapter
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupRecyclerView()
        setupListeners()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        recipesAdapter = RecipesAdapter(emptyList())
        binding.searchResultRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recipesAdapter
        }
    }

    private fun setupListeners() {
        binding.searchButton.setOnClickListener {
            val ingredient = binding.ingredientEditText.text.toString().trim()
            if (ingredient.isNotEmpty()) {
                searchRecipes(ingredient)
            }
        }
    }

    private fun searchRecipes(ingredient: String) {
        firestore.collection("recipes")
            .whereArrayContains("ingredients", ingredient)
            .get()
            .addOnSuccessListener { result ->
                val recipes = result.toObjects(Recipe::class.java)
                Log.d("RecipeFragment", "Recipes found: $recipes")
                updateRecyclerView(recipes)
            }
            .addOnFailureListener { e ->
                Log.e("RecipeFragment", "Error fetching recipes", e)
            }
    }

    private fun updateRecyclerView(recipes: List<Recipe>) {
        recipesAdapter.updateData(recipes)
    }
}
