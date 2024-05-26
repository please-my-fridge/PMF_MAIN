package com.example.pmf

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SubActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var numEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ingre_del_refix_sub)

        nameEditText = findViewById(R.id.nameEditText)
        numEditText = findViewById(R.id.numEditText)
        saveButton = findViewById(R.id.saveButton)
        dbHelper = DBHelper(this)

        val name = intent.getStringExtra("name")
        val num = intent.getIntExtra("num", 0)

        nameEditText.setText(name.toString())
        numEditText.setText(num.toString())

        saveButton.setOnClickListener {
            val newNum = numEditText.text.toString().toInt()
            dbHelper.updateItem(name ?: "", newNum)
            Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
