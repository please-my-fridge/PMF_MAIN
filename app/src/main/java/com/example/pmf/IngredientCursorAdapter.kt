package com.example.pmf

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView

class IngredientCursorAdapter(context: Context, cursor: Cursor?) : CursorAdapter(context, cursor, 0) {

    override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
        return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false)
    }

    override fun bindView(view: View, context: Context, cursor: Cursor) {
        val text1 = view.findViewById<TextView>(android.R.id.text1)
        val text2 = view.findViewById<TextView>(android.R.id.text2)

        val name = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_NAME))
        val num = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_NUM))

        text1.text = name
        text2.text = num.toString()
    }
}
