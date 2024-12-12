package com.example.tiptime

import org.junit.Assert
import org.junit.Test
import java.text.NumberFormat

class TipCalculatorClass {

    @Test
    fun calculateTip_20PercentNoRoundUp() {
        val amount = 10.00
        val tipPercent = 20.00

        val expectedTip = NumberFormat.getCurrencyInstance().format(2)
        val actureTip = calculateTip(amount = amount, tipPercent = tipPercent, false)

        Assert.assertEquals(expectedTip, actureTip)
    }
}