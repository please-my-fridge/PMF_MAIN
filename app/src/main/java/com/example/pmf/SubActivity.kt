package com.example.pmf

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SubActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var numEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ingre_resis_sub)

        nameEditText = findViewById(R.id.ingredientNameAutoComplete)
        numEditText = findViewById(R.id.ingredientQuantity)
        saveButton = findViewById(R.id.addIngredientButton)
        dbHelper = DBHelper(this)

        val name = intent.getStringExtra("name")
        val num = intent.getIntExtra("num", 0)

        nameEditText.setText(name)
        numEditText.setText(num.toString())

        saveButton.setOnClickListener {
            try {
                val newNum = numEditText.text.toString().toInt()
                dbHelper.updateItem(name ?: "", newNum)
                Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
