package com.example.pmf.ui.main.elements

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pmf.DB.Ingredient
import com.example.pmf.R

class IngredientAdapter(
    private var ingredients: List<Ingredient>,
    private val onEditClick: (Ingredient) -> Unit,
    private val onDeleteClick: (Ingredient) -> Unit
) : RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {
    fun updateIngredients(newIngredients: List<Ingredient>) {
        ingredients = newIngredients
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ingredient, parent, false)
        return IngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient = ingredients[position]
        holder.bind(ingredient)
        holder.editButton.setOnClickListener { onEditClick(ingredient) }
        holder.deleteButton.setOnClickListener { onDeleteClick(ingredient) }
    }

    override fun getItemCount(): Int = ingredients.size

    class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val purchaseDateTextView: TextView = itemView.findViewById(R.id.purchaseDateTextView)
        private val expiryDateTextView: TextView = itemView.findViewById(R.id.expiryDateTextView)
        private val storageLocationTextView: TextView = itemView.findViewById(R.id.storageLocationTextView)
        val editButton: Button = itemView.findViewById(R.id.editButton)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)

        fun bind(ingredient: Ingredient) {
            nameTextView.text = ingredient.name
            purchaseDateTextView.text = ingredient.purchaseDate
            expiryDateTextView.text = ingredient.expiryDate
            storageLocationTextView.text = ingredient.storageLocation
        }
    }
}
