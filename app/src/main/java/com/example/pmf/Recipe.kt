package com.example.pmf

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val name: String = "",
    val ingredients: List<String> = emptyList(),
    val steps: List<String> = emptyList()
) : Parcelable
