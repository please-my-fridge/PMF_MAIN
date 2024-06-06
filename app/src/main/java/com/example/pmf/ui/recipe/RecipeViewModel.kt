package com.example.pmf.ui.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pmf.DB.DBHelper

class RecipeViewModel(private val dbHelper: DBHelper) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun recommendRecipe() {
        // 추천 레시피 생성
        val recommendedRecipe = StringBuilder()
        recommendedRecipe.append("추천된 레시피:\n")

        // 사용자가 보유한 식재료
        val userIngredients = dbHelper.getUserIngredients()

        // 레시피 목록
        val recipes = mapOf(
            "김치밥" to setOf("김치", "밥"),
            "김치전" to setOf("김치", "밀가루"),
            "간장계란밥" to setOf("계란", "밥", "간장"),
            "김치라면" to setOf("김치", "면"),
            "토마토파스타" to setOf("면", "tomato"),
            "전주비빔밥3" to setOf("당근", "오이", "밥", "계란", "고사리"),
            "전주비빔밥4" to setOf("당근", "오이", "밥", "계란", "고사리", "고사리2"),
            "전주비빔밥5" to setOf("당근", "오이", "밥", "계란", "고사리", "고사리2", "고사리3")
        )

        // 각 레시피의 등수를 계산하여 맵에 저장
        val recommendedRecipes = recipes.mapNotNull { (recipe, ingredients) ->
            val missingIngredients = ingredients.subtract(userIngredients)
            if (missingIngredients.size == ingredients.size) {
                // 재료가 하나도 겹치지 않으면 추천에서 제외
                null
            } else {
                val ranking = when (missingIngredients.size) {
                    0 -> 1 to "최우선 추천메뉴"
                    1 -> 2 to "재료 하나 부족"
                    2 -> 3 to "재료 두개 부족"
                    else -> 4 to "재료 세개 이상 부족"
                }
                recipe to ranking
            }
        }

        // 등수가 높은 순으로 레시피를 정렬하여 출력
        recommendedRecipes.sortedBy { it.second.first }.forEach { (recipe, ranking) ->
            recommendedRecipe.append("$recipe : ${ranking.second}\n")
        }

        // 결과 출력
        _text.value = recommendedRecipe.toString()
    }
}
