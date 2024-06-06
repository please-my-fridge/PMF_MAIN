package com.example.pmf.ui.ingredient

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.pmf.DB.Ingredient
import com.example.pmf.R

class IngredientListAdapter(private val context: Context, private val dataSource: List<Ingredient>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = convertView ?: inflater.inflate(R.layout.list_item_ingredient, parent, false)

        val nameTextView = rowView.findViewById<TextView>(R.id.ingredient_name)
        val purchaseDateTextView = rowView.findViewById<TextView>(R.id.ingredient_purchase_date)
        val expiryDateTextView = rowView.findViewById<TextView>(R.id.ingredient_expiry_date)
        val storageLocationTextView = rowView.findViewById<TextView>(R.id.ingredient_storage_location)

        val ingredient = getItem(position) as Ingredient

        nameTextView.text = ingredient.name
        purchaseDateTextView.text = ingredient.purchaseDate
        expiryDateTextView.text = ingredient.expiryDate
        storageLocationTextView.text = ingredient.storageLocation

        return rowView
    }
}
