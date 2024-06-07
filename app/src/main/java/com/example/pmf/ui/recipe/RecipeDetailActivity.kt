package com.example.pmf.ui.recipe

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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        // 현재 액티비티를 종료하고, 부모 액티비티로 이동합니다.
        finish()
        return true
    }
}
