package com.example.cupcake.test

import android.icu.util.Calendar
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.cupcake.CupcakeApp
import com.example.cupcake.CupcakeScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Locale
import com.example.cupcake.R

class CupcakeScreenNavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    // 모든 테스트를 시작전 반드시 거쳐야 하는 작업으로 @Before 주석으로 사용하여 표현
    @Before
    fun setupCupcakeNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            CupcakeApp(navController = navController)
        }
    }

    // 첫 화면이 주문 시작 화면으로 나타나는지 테스트
    @Test
    fun cupcakeNavHost_verifyStartDestination() {
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    // 첫 화면에서는 뒤로가기 버튼이 안타나는지 테스트
    @Test
    fun cupcakeNavHost_verifyBackNavigationNotShownOnStartOrderScreen() {
        val backText = composeTestRule.activity.getString(com.example.cupcake.R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).assertDoesNotExist()
    }

    // 첫 화면에서 one cupcake 옵션을 선택하면 맛 선택 화면으로 이동하는지 테스트
    @Test
    fun cupcakeNavHost_clickOneCupcake_navigatesToSelectFlavorScreen() {
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.one_cupcake)
            .performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Flavor.name)
    }

    // 맛 선택 화면으로 이동할 수 있게 도와주는 도우미 메서드
    private fun navigateToFlavorScreen() {
        composeTestRule.onNodeWithStringId(R.string.one_cupcake)
            .performClick()
        composeTestRule.onNodeWithStringId(R.string.chocolate)
            .performClick()
    }

    // 수령 화면으로 이동할 수 있게 도와주는 도우미 메서드
    private fun navigateToPickupScreen() {
        navigateToFlavorScreen()
        composeTestRule.onNodeWithStringId(R.string.next).performClick()
    }

    // 수령일을 선택할 수 있게 오늘 날짜를 포맷에 맞는 형태로 반환해주는 도우미 메서드
    private fun getFormattedDate(): String {
        val calendar = Calendar.getInstance()
        calendar.add(java.util.Calendar.DATE, 1)
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        return formatter.format(calendar.time)
    }

    // 요약 화면으로 이동할 수 있게 도와주는 도우미 메서드
    private fun navigateToSummaryScreen() {
        navigateToPickupScreen()
        composeTestRule.onNodeWithText(getFormattedDate()).performClick()
        composeTestRule.onNodeWithStringId(R.string.next).performClick()
    }

    // 현재 화면에서 위로 버튼을 클릭하여 뒤로 갈 수 있게 도와주는  도우미 메서드
    private fun performNavigateUp() {
        val backText = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).performClick()
    }

    // 맛 화면에서 위로 버튼을 클릭하여 시작 화면으로 이동하는지 테스트
    @Test
    fun cupcakeNavHost_clickBackOnFlavorScreen_navigatesToStartOrderScreen() {
        navigateToFlavorScreen()
        performNavigateUp()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    // 맛 화면에서 취소 버튼을 클릭해서 시작 화면으로 이동하는지 테스트
    @Test
    fun cupcakeNavHost_clickCancelOnFlavorScreen_navigatesToStartOrderScreen() {
        navigateToFlavorScreen()
        composeTestRule.onNodeWithStringId(R.string.cancel).performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    // 수령 화면으로 이동하는지 테스트
    @Test
    fun cupcakeNavHost_clickNextOnFlavorScreen_navigatesToPickupScreen() {
        navigateToFlavorScreen()
        composeTestRule.onNodeWithStringId(R.string.next).performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Pickup.name)
    }

    // 수령 화면에서 위로 버튼을 클릭하면 맛 화면으로 이동하는지 테스트
    @Test
    fun cupcakeNavHost_clickBackOnPickupScreen_navigatesToFlavorScreen() {
        navigateToPickupScreen()
        performNavigateUp()
        navController.assertCurrentRouteName(CupcakeScreen.Flavor.name)
    }

    // 수령 화면에서 취소 버튼을 클릭하면 시작 화면으로 이동하는지 테스트
    @Test
    fun cupcakeNavHost_clickCancelOnPickupScreen_navigatesToStartOrderScreen() {
        navigateToPickupScreen()
        composeTestRule.onNodeWithStringId(R.string.cancel).performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    // 요약 화면으로 이동하는지 테스트
    @Test
    fun cupcakeNavHost_clickNextOnPickupScreen_navigatesToSummaryScreen() {
        navigateToPickupScreen()
        composeTestRule.onNodeWithText(getFormattedDate()).performClick()
        composeTestRule.onNodeWithStringId(R.string.next).performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Summary.name)
    }

    // 요약 화면에서 취소 버튼을 클릭하면 시작 화면으로 이동하는지 테스트
    @Test
    fun cupcakeNavHost_clickCancelOnSummaryScreen_navigatesToStartOrderScreen() {
        navigateToSummaryScreen()
        composeTestRule.onNodeWithStringId(R.string.cancel).performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }
}