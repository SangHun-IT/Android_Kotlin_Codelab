package com.example.tiptime

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.example.tiptime.ui.theme.TipTimeTheme
import org.junit.Rule
import org.junit.Test
import java.text.NumberFormat
import java.util.Locale

class TipUITests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun calculate_20_percent_tip() {
        composeTestRule.setContent {
            TipTimeTheme {
                TipTimeLayout()
            }
        }
        /* 실제로 디자인 한 UI는 compposeTestRule 객체를 통해 액세스하고 사용이 가능하다. */
        composeTestRule.onNodeWithText("Bill Amount")
            .performTextInput("10")

        composeTestRule.onNodeWithText("Tip Percentage")
            .performTextInput("20")

        /* 기대 결과 값 */
        val expectedTip = NumberFormat.getCurrencyInstance(Locale.US).format(2)

        /* 기대 결과 값으로 표시가 된 Text가 존재하는지를 테스트하여 UI 실행 결과를 검증*/
        composeTestRule.onNodeWithText("Tip Amount: $expectedTip").assertExists(
            "No node with this text was found"
        )
    }
}