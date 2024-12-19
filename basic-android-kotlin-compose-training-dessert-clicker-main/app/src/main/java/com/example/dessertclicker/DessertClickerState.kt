package com.example.dessertclicker

import com.example.dessertclicker.data.Datasource
import com.example.dessertclicker.model.Dessert

data class DessertClickerState (
    val revenue: Int = 0,
    val dessertsSold: Int = 0,
    val currentDessert: Dessert = Datasource.dessertList[0]
)