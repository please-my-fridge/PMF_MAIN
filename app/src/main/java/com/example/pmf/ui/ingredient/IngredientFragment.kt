package com.example.pmf.ui.ingredient

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.pmf.DB.DBHelper
import com.example.pmf.DB.Ingredient
import com.example.pmf.R
import java.text.SimpleDateFormat
import java.util.*

class IngredientFragment : Fragment() {

    private lateinit var dbHelper: DBHelper
    private lateinit var selectedPurchaseDate: String
    private lateinit var selectedExpiryDate: String
    private lateinit var selectedIngredient: String
    private lateinit var selectIngredientButton: Button
    private val sharedViewModel: IngredientViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root = inflater.inflate(R.layout.fragment_ingredient, container, false)

        dbHelper = DBHelper(requireContext())

        selectIngredientButton = root.findViewById(R.id.btn_select_ingredient)
        val btnAdd: Button = root.findViewById(R.id.btn_add)
        val btnPurchaseDate: Button = root.findViewById(R.id.btn_purchase_date)
        val btnExpiryDate: Button = root.findViewById(R.id.btn_expiry_date)
        val spinnerStorageLocation: Spinner = root.findViewById(R.id.spinner_storage_location)

        selectIngredientButton.setOnClickListener {
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val ingredientSearchDialogFragment = IngredientSearchDialogFragment()
            ingredientSearchDialogFragment.show(fragmentManager, "ingredient_search_dialog")
            Log.d("IngredientFragment", "IngredientSearchDialogFragment shown")
        }

        // 보관 장소 스피너 설정
        val storageLocations = arrayOf("냉장고", "냉동고", "실온")
        val storageAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, storageLocations)
        storageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerStorageLocation.adapter = storageAdapter

        // 날짜 선택 설정
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        btnPurchaseDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(requireContext(), { _, year, month, day ->
                calendar.set(year, month, day)
                selectedPurchaseDate = dateFormatter.format(calendar.time)
                btnPurchaseDate.text = selectedPurchaseDate
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            datePicker.show()
        }

        btnExpiryDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(requireContext(), { _, year, month, day ->
                calendar.set(year, month, day)
                selectedExpiryDate = dateFormatter.format(calendar.time)
                btnExpiryDate.text = selectedExpiryDate
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            datePicker.show()
        }

        btnAdd.setOnClickListener {
            val storageLocation = spinnerStorageLocation.selectedItem.toString()

            if (::selectedIngredient.isInitialized && ::selectedPurchaseDate.isInitialized && ::selectedExpiryDate.isInitialized) {
                val ingredient = Ingredient(selectedIngredient, selectedPurchaseDate, selectedExpiryDate, storageLocation)
                dbHelper.addItem(ingredient.name, ingredient.purchaseDate, ingredient.expiryDate, ingredient.storageLocation)
                Toast.makeText(requireContext(), "재료가 추가되었습니다.", Toast.LENGTH_SHORT).show()
                sharedViewModel.addIngredient(ingredient)
            } else {
                Toast.makeText(requireContext(), "모든 항목을 선택해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("IngredientFragment", "Setting FragmentResultListener in onViewCreated")

        sharedViewModel.selectedIngredient.observe(viewLifecycleOwner) { ingredient ->
            Log.d("IngredientFragment", "Received ingredient: $ingredient")
            selectedIngredient = ingredient
            selectIngredientButton.text = ingredient
        }
    }
}
