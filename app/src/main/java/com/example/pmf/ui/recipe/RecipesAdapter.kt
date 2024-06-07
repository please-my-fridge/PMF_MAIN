package com.example.pmf.ui.recipe

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pmf.databinding.FragmentRecipeItemBinding

class RecipesAdapter(private var recipes: List<Recipe>) :
    RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = FragmentRecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    override fun getItemCount(): Int = recipes.size

    fun updateData(newRecipes: List<Recipe>) {
        recipes = newRecipes
        notifyDataSetChanged()
    }

    inner class RecipeViewHolder(private val binding: FragmentRecipeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe) {
            binding.recipe = recipe
            binding.executePendingBindings()

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, RecipeDetailActivity::class.java).apply {
                    putExtra("recipe", recipe)
                }
                itemView.context.startActivity(intent)
            }
        }
    }
}
