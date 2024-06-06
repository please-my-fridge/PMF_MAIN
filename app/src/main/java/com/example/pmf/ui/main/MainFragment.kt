package com.example.pmf.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.pmf.DB.DBHelper
import com.example.pmf.DB.Ingredient
import com.example.pmf.R
import com.example.pmf.databinding.FragmentMainBinding
import com.example.pmf.ui.main.elements.ColdStorageFragment
import com.example.pmf.ui.main.elements.FreezeFragment
import com.example.pmf.ui.main.elements.RoomTemperatureStorageFragment
import com.example.pmf.ui.ingredient.IngredientViewModel

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var dbHelper: DBHelper
    private val sharedViewModel: IngredientViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbHelper = DBHelper(requireContext())

        // 초기 프래그먼트 설정
        setFragment(ColdStorageFragment(), sharedViewModel.coldStorageIngredients.value ?: emptyList())

        // 각 버튼에 클릭 리스너 설정
        binding.buttonFragment1.setOnClickListener { setFragment(ColdStorageFragment(), sharedViewModel.coldStorageIngredients.value ?: emptyList()) }
        binding.buttonFragment2.setOnClickListener { setFragment(FreezeFragment(), sharedViewModel.freezeStorageIngredients.value ?: emptyList()) }
        binding.buttonFragment3.setOnClickListener { setFragment(RoomTemperatureStorageFragment(), sharedViewModel.roomTemperatureStorageIngredients.value ?: emptyList()) }

        // ViewModel로부터 데이터 수신
        sharedViewModel.ingredientAdded.observe(viewLifecycleOwner) { ingredient ->
            addIngredientToStorage(ingredient)
        }
    }

    private fun setFragment(fragment: Fragment, ingredients: List<Ingredient>) {
        val bundle = Bundle().apply {
            putParcelableArrayList("ingredients", ArrayList(ingredients))
        }
        fragment.arguments = bundle

        childFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            commitNow()
        }
    }

    private fun addIngredientToStorage(ingredient: Ingredient) {
        when (ingredient.storageLocation) {
            "냉장고" -> {
                sharedViewModel.addIngredientToColdStorage(ingredient)
                if (childFragmentManager.findFragmentById(R.id.fragmentContainer) is ColdStorageFragment) {
                    setFragment(ColdStorageFragment(), sharedViewModel.coldStorageIngredients.value ?: emptyList())
                }
            }
            "냉동고" -> {
                sharedViewModel.addIngredientToFreezeStorage(ingredient)
                if (childFragmentManager.findFragmentById(R.id.fragmentContainer) is FreezeFragment) {
                    setFragment(FreezeFragment(), sharedViewModel.freezeStorageIngredients.value ?: emptyList())
                }
            }
            "실온" -> {
                sharedViewModel.addIngredientToRoomTemperatureStorage(ingredient)
                if (childFragmentManager.findFragmentById(R.id.fragmentContainer) is RoomTemperatureStorageFragment) {
                    setFragment(RoomTemperatureStorageFragment(), sharedViewModel.roomTemperatureStorageIngredients.value ?: emptyList())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
