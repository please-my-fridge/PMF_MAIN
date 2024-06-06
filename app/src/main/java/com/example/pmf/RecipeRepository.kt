package com.example.pmf

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeRepository {

    private val apiService = RetrofitClient.getClient("https://jsonplaceholder.typicode.com/")
        .create(RecipeApiService::class.java)

    fun getRecommendedRecipes(ingredients: List<String>): LiveData<List<Recipe>> {
        val data = MutableLiveData<List<Recipe>>()

        apiService.getRecipes(ingredients).enqueue(object : Callback<List<Recipe>> {
            override fun onResponse(call: Call<List<Recipe>>, response: Response<List<Recipe>>) {
                if (response.isSuccessful) {
                    data.value = response.body()
                } else {
                    data.value = emptyList()
                }
            }

            override fun onFailure(call: Call<List<Recipe>>, t: Throwable) {
                data.value = emptyList()
            }
        })

        return data
    }
}
