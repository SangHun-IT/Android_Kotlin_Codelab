package com.example.cupcake.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.cupcake.ui.SelectOptionScreen
import org.junit.Rule
import org.junit.Test
import com.example.cupcake.R

class CupcakeOrderScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun selectOptionScreen_verifyContent() {
        // 미리 맛 옵션을 제공
        val flavors = listOf("Vanilla", "Chocolate", "Hazelnut", "Cookie", "Mango")
        // 합계를 미리 계산
        val subtotal = "$100"

        // SelectOption 화면이 나타나게끔 Test Rule을 세팅
        composeTestRule.setContent {
            SelectOptionScreen(subtotal = subtotal, options = flavors)
        }

        // 각 옵션이 화면에 보여지고 있는지 Test
        flavors.forEach { flavor ->
            composeTestRule.onNodeWithText(flavor).assertIsDisplayed()
        }

        // 합계 금액이 화면에 $100로 표시되고 있는지 Test
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                com.example.cupcake.R.string.subtotal_price,
                subtotal
            )
        ).assertIsDisplayed()

        // next button을 사용할 수 없는지 테스트
        composeTestRule.onNodeWithStringId(R.string.next).assertIsNotEnabled()
    }
}