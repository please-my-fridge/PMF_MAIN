package com.example.pmf

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.ListView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity

class SSubActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper
    private lateinit var searchView: SearchView
    private lateinit var resultListView: ListView
    private lateinit var adapter: IngredientCursorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ingre_del_ingre_refix)

        dbHelper = DBHelper(this)
        searchView = findViewById(R.id.rrqo00au0hu)
        resultListView = findViewById(R.id.resultListView)

        setupSearchView()

        resultListView.setOnItemClickListener { parent, view, position, id ->
            val cursor = adapter.getItem(position) as Cursor
            val name = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_NAME))
            val num = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_NUM))
            val intent = Intent(this, SubActivity::class.java).apply {
                putExtra("name", name)
                putExtra("num", num)
            }
            startActivity(intent)
        }
    }

    private fun setupSearchView() {
        val cursor = dbHelper.getAllIngredientsCursor()
        adapter = IngredientCursorAdapter(this, cursor)
        resultListView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    updateAdapter(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                updateAdapter(newText ?: "")
                return true
            }
        })
    }

    private fun updateAdapter(query: String) {
        val cursor = dbHelper.searchItemsCursor(query)
        adapter.changeCursor(cursor)
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.changeCursor(null)
    }
}
