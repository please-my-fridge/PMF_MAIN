package com.example.pmf.ui.recipe

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RecipeApiService {
    @POST("recipes")
    fun getRecipes(@Body ingredients: List<String>): Call<List<Recipe>>
}
