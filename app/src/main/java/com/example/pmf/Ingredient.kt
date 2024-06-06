package com.example.pmf

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Ingredient(
    @PrimaryKey val name: String,
    val quantity: Int
)