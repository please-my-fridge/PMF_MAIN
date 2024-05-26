package com.example.pmf



import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity

class SSubActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper
    private lateinit var searchView: SearchView
    private lateinit var resultListView: ListView
    private lateinit var adapter: IngredientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ingre_del_ingre_refix)

        dbHelper = DBHelper(this)
        searchView = findViewById(R.id.rrqo00au0hu)
        resultListView = findViewById(R.id.resultListView)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    val results = dbHelper.searchItems(query)
                    adapter = IngredientAdapter(this@SSubActivity, results)
                    resultListView.adapter = adapter
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        resultListView.setOnItemClickListener { _, _, position, _ ->
            val selectedIngredient = adapter.getItem(position)
            val intent = Intent(this, SubActivity::class.java).apply {
                putExtra("name", selectedIngredient?.first)
                putExtra("num", selectedIngredient?.second)
            }
            startActivity(intent)
        }
    }
}
