package com.example.pmf.ui.ingredient

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.pmf.DB.BasicIngredientsDBHelper
import com.example.pmf.DB.BasicIngredient
import com.example.pmf.R

class IngredientSearchDialogFragment : DialogFragment() {

    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var listView: ListView
    private lateinit var dbHelper: BasicIngredientsDBHelper
    private val sharedViewModel: IngredientViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ingredient_search_dialog, container, false)

        searchEditText = view.findViewById(R.id.edit_text_search)
        searchButton = view.findViewById(R.id.button_search)
        listView = view.findViewById(R.id.list_view_search_results)
        dbHelper = BasicIngredientsDBHelper(requireContext())

        // 데이터베이스 초기화
        resetDatabase()

        searchButton.setOnClickListener {
            val query = searchEditText.text.toString()
            val results = dbHelper.searchItems(query)
            Log.d("IngredientSearchDialog", "Search results: $results")
            updateListView(results)
        }

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedIngredient = listView.adapter.getItem(position) as BasicIngredient
            sharedViewModel.selectIngredient(selectedIngredient.name)
            Log.d("IngredientSearchDialog", "Selected ingredient: ${selectedIngredient.name}")
            dismiss()  // 다이얼로그 닫기
        }

        return view
    }

    private fun updateListView(results: List<BasicIngredient>) {
        val adapter = BasicIngredientListAdapter(requireContext(), results)
        listView.adapter = adapter
    }

    private fun resetDatabase() {
        val context = requireContext()
        if (BasicIngredientsDBHelper.deleteDatabase(context)) {
            Log.d("IngredientSearchDialog", "Database deleted successfully")
            val dbHelper = BasicIngredientsDBHelper(context)
            dbHelper.writableDatabase
            Log.d("IngredientSearchDialog", "Database reset successfully")
            BasicIngredientsDBHelper.logDatabasePath(context)
        } else {
            Log.d("IngredientSearchDialog", "Failed to delete database")
        }
    }
}
