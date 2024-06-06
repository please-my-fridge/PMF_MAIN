package com.example.pmf.ui.ingredient

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.pmf.DB.BasicIngredient
import com.example.pmf.R

class BasicIngredientListAdapter(private val context: Context, private val dataSource: List<BasicIngredient>) : BaseAdapter() {

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
        val rowView = convertView ?: inflater.inflate(R.layout.list_item_basic_ingredient, parent, false)

        val nameTextView = rowView.findViewById(R.id.basic_ingredient_name) as TextView

        val ingredient = getItem(position) as BasicIngredient

        nameTextView.text = ingredient.name

        return rowView
    }

}
