package com.example.pmf

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class IngredientAdapter(context: Context, private val ingredients: List<Pair<String, Int>>) : ArrayAdapter<Pair<String, Int>>(context, 0, ingredients) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false)
        val ingredient = getItem(position)

        val text1 = view.findViewById<TextView>(android.R.id.text1)
        val text2 = view.findViewById<TextView>(android.R.id.text2)

        text1.text = ingredient?.first
        text2.text = ingredient?.second.toString()

        return view
    }
}
