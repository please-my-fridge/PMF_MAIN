package com.example.pmf.ui.ingredient

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.pmf.DB.DBHelper
import com.example.pmf.DB.Ingredient
import com.example.pmf.R

class EditIngredientDialogFragment(
    private val ingredient: Ingredient,
    private val onSave: (Ingredient) -> Unit
) : DialogFragment() {

    private lateinit var dbHelper: DBHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_ingredient_dialog, container, false)
        dbHelper = DBHelper(requireContext())

        val nameEditText = view.findViewById<EditText>(R.id.edit_text_name)
        val purchaseDateEditText = view.findViewById<EditText>(R.id.edit_text_purchase_date)
        val expiryDateEditText = view.findViewById<EditText>(R.id.edit_text_expiry_date)
        val storageLocationEditText = view.findViewById<EditText>(R.id.edit_text_storage_location)
        val saveButton = view.findViewById<Button>(R.id.button_save)

        nameEditText.setText(ingredient.name)
        purchaseDateEditText.setText(ingredient.purchaseDate)
        expiryDateEditText.setText(ingredient.expiryDate)
        storageLocationEditText.setText(ingredient.storageLocation)

        saveButton.setOnClickListener {
            val updatedIngredient = Ingredient(
                nameEditText.text.toString(),
                purchaseDateEditText.text.toString(),
                expiryDateEditText.text.toString(),
                storageLocationEditText.text.toString()
            )
            dbHelper.updateItem(updatedIngredient.name, updatedIngredient.purchaseDate, updatedIngredient.expiryDate, updatedIngredient.storageLocation)
            onSave(updatedIngredient)
            dismiss()
        }

        return view
    }
}
