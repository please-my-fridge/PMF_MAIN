package com.example.pmf

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pmf.databinding.FragmentRecipeDetailBinding

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var binding: FragmentRecipeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()

        val recipe: Recipe? = intent.getParcelableExtra("recipe")
        if (recipe != null) {
            binding.recipe = recipe
            binding.stepsTextView.text = recipe.steps.joinToString("\n") { step -> step }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}
