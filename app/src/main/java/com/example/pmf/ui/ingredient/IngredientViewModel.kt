package com.example.pmf.ui.ingredient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pmf.DB.Ingredient

class IngredientViewModel : ViewModel() {

    private val _selectedIngredient = MutableLiveData<String>()
    val selectedIngredient: LiveData<String> get() = _selectedIngredient

    private val _coldStorageIngredients = MutableLiveData<List<Ingredient>>(emptyList())
    val coldStorageIngredients: LiveData<List<Ingredient>> get() = _coldStorageIngredients

    private val _freezeStorageIngredients = MutableLiveData<List<Ingredient>>(emptyList())
    val freezeStorageIngredients: LiveData<List<Ingredient>> get() = _freezeStorageIngredients

    private val _roomTemperatureStorageIngredients = MutableLiveData<List<Ingredient>>(emptyList())
    val roomTemperatureStorageIngredients: LiveData<List<Ingredient>> get() = _roomTemperatureStorageIngredients

    private val _ingredientAdded = MutableLiveData<Ingredient>()
    val ingredientAdded: LiveData<Ingredient> get() = _ingredientAdded

    fun selectIngredient(ingredient: String) {
        _selectedIngredient.value = ingredient
    }

    fun addIngredient(ingredient: Ingredient) {
        when (ingredient.storageLocation) {
            "냉장고" -> addIngredientToColdStorage(ingredient)
            "냉동고" -> addIngredientToFreezeStorage(ingredient)
            "실온" -> addIngredientToRoomTemperatureStorage(ingredient)
        }
        _ingredientAdded.value = ingredient
    }

    fun setColdStorageIngredients(ingredients: List<Ingredient>) {
        _coldStorageIngredients.value = ingredients
    }

    fun setFreezeStorageIngredients(ingredients: List<Ingredient>) {
        _freezeStorageIngredients.value = ingredients
    }

    fun setRoomTemperatureStorageIngredients(ingredients: List<Ingredient>) {
        _roomTemperatureStorageIngredients.value = ingredients
    }

    fun addIngredientToColdStorage(ingredient: Ingredient) {
        val currentList = _coldStorageIngredients.value?.toMutableList() ?: mutableListOf()
        currentList.add(ingredient)
        _coldStorageIngredients.value = currentList
    }

    fun addIngredientToFreezeStorage(ingredient: Ingredient) {
        val currentList = _freezeStorageIngredients.value?.toMutableList() ?: mutableListOf()
        currentList.add(ingredient)
        _freezeStorageIngredients.value = currentList
    }

    fun addIngredientToRoomTemperatureStorage(ingredient: Ingredient) {
        val currentList = _roomTemperatureStorageIngredients.value?.toMutableList() ?: mutableListOf()
        currentList.add(ingredient)
        _roomTemperatureStorageIngredients.value = currentList
    }

    fun updateIngredient(updatedIngredient: Ingredient) {
        when (updatedIngredient.storageLocation) {
            "냉장고" -> updateIngredients(_coldStorageIngredients, updatedIngredient)
            "냉동고" -> updateIngredients(_freezeStorageIngredients, updatedIngredient)
            "실온" -> updateIngredients(_roomTemperatureStorageIngredients, updatedIngredient)
        }
    }

    private fun updateIngredients(storage: MutableLiveData<List<Ingredient>>, updatedIngredient: Ingredient) {
        val currentList = storage.value?.toMutableList() ?: mutableListOf()
        val index = currentList.indexOfFirst { it.name == updatedIngredient.name && it.purchaseDate == updatedIngredient.purchaseDate }
        if (index >= 0) {
            currentList[index] = updatedIngredient
            storage.value = currentList
        }
    }

    fun deleteIngredient(ingredient: Ingredient) {
        when (ingredient.storageLocation) {
            "냉장고" -> deleteIngredients(_coldStorageIngredients, ingredient)
            "냉동고" -> deleteIngredients(_freezeStorageIngredients, ingredient)
            "실온" -> deleteIngredients(_roomTemperatureStorageIngredients, ingredient)
        }
    }

    private fun deleteIngredients(storage: MutableLiveData<List<Ingredient>>, ingredient: Ingredient) {
        val currentList = storage.value?.toMutableList() ?: mutableListOf()
        currentList.removeAll { it.name == ingredient.name && it.purchaseDate == ingredient.purchaseDate }
        storage.value = currentList
    }
}
