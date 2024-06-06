package com.example.pmf.ui.main.elements

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pmf.DB.DBHelper
import com.example.pmf.DB.Ingredient
import com.example.pmf.R
import com.example.pmf.ui.ingredient.EditIngredientDialogFragment
import com.example.pmf.ui.ingredient.IngredientViewModel

class RoomTemperatureStorageFragment : Fragment() {

    private lateinit var dbHelper: DBHelper
    private lateinit var roomTempRecyclerView: RecyclerView
    private lateinit var ingredientAdapter: IngredientAdapter
    private val sharedViewModel: IngredientViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_roomtemperaturestorage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = DBHelper(requireContext())
        roomTempRecyclerView = view.findViewById(R.id.roomTemperatureRecyclerView)
        roomTempRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        ingredientAdapter = IngredientAdapter(sharedViewModel.roomTemperatureStorageIngredients.value ?: emptyList(), { ingredient ->
            showEditIngredientDialog(ingredient)
        }, { ingredient ->
            deleteIngredient(ingredient)
        })
        roomTempRecyclerView.adapter = ingredientAdapter

        // ViewModel로부터 데이터 수신
        sharedViewModel.roomTemperatureStorageIngredients.observe(viewLifecycleOwner) { ingredients ->
            ingredientAdapter.updateIngredients(ingredients)
            Log.d("RoomTemperatureStorageFragment", "Ingredients updated: $ingredients")
        }
    }

    private fun showEditIngredientDialog(ingredient: Ingredient) {
        val dialogFragment = EditIngredientDialogFragment(ingredient) { updatedIngredient ->
            // 다이얼로그에서 수정된 데이터를 저장하고 목록을 새로고침
            sharedViewModel.updateIngredient(updatedIngredient)
            Log.d("RoomTemperatureStorageFragment", "Ingredient updated: $updatedIngredient")
        }
        dialogFragment.show(childFragmentManager, "editIngredientDialog")
    }

    private fun deleteIngredient(ingredient: Ingredient) {
        dbHelper.deleteItem(ingredient.name, ingredient.purchaseDate)
        sharedViewModel.deleteIngredient(ingredient)
        Log.d("RoomTemperatureStorageFragment", "Ingredient deleted: $ingredient")
    }
}
