package com.example.cringehub

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cringehub.navigation.MainNavHost
import com.example.cringehub.ui.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        hiltRule.inject()
        composeTestRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            MainNavHost(navController = navController)
        }
    }

    @Test
    fun mainNavHost_verifyStartDestination() {
        composeTestRule
            .onNodeWithContentDescription("Initial Auth screen")
            .assertIsDisplayed()
    }

    @Test
    fun mainNavHost_clickAllProfiles_navigateToProfiles() {
        composeTestRule.onNodeWithContentDescription("Hub screen")
            .performClick()

//        assertTrue(navController.currentBackStackEntry?.destination?.hasRoute<HubRoute>() ?: false)
    }
}