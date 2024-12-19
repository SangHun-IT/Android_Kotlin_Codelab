package com.example.dessertclicker

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.dessertclicker.data.Datasource
import com.example.dessertclicker.model.Dessert
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DessertClickerViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DessertClickerState())

    val uiState: StateFlow<DessertClickerState> = _uiState.asStateFlow()
    val desserts: List<Dessert> = Datasource.dessertList

    // 사용자가 디저트를 클릭해서 팔았을 때
    fun soldDessert() {

        val increasedRevenue = _uiState.value.revenue + _uiState.value.currentDessert.price
        val increasedDessertsSold = _uiState.value.dessertsSold + 1

        _uiState.update { currentState ->
            currentState.copy(
                revenue = increasedRevenue,
                dessertsSold = increasedDessertsSold,
                // 증가된 값에 맞는 디저트로 변경
                currentDessert = determineDessertToShow(increasedDessertsSold)
            )
        }
    }

    /**
     * Determine which dessert to show.
     */
    private fun determineDessertToShow(
        dessertsSold: Int
    ): Dessert {
        var dessertToShow = desserts.first()
        for (dessert in desserts) {
            if (dessertsSold >= dessert.startProductionAmount) {
                dessertToShow = dessert
            } else {
                // The list of desserts is sorted by startProductionAmount. As you sell more desserts,
                // you'll start producing more expensive desserts as determined by startProductionAmount
                // We know to break as soon as we see a dessert who's "startProductionAmount" is greater
                // than the amount sold.
                break
            }
        }

        return dessertToShow
    }
}