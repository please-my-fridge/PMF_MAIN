package com.example.pmf

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pmf.databinding.FragmentRecipeBinding
import com.google.firebase.firestore.FirebaseFirestore

class RecipeActivity : AppCompatActivity() {

    private lateinit var binding: FragmentRecipeBinding
    private lateinit var recipesAdapter: RecipesAdapter
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupListeners()
    }

    private fun setupRecyclerView() {
        recipesAdapter = RecipesAdapter(emptyList())
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@RecipeActivity)
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
                Log.d("RecipeActivity", "Recipes found: $recipes")
                updateRecyclerView(recipes)
            }
            .addOnFailureListener { e ->
                Log.e("RecipeActivity", "Error fetching recipes", e)
            }
    }

    private fun updateRecyclerView(recipes: List<Recipe>) {
        recipesAdapter.updateData(recipes)
    }
}
