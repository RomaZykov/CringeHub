package com.example.admin

import android.content.Context
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.Configuration
import androidx.work.impl.utils.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import com.example.adminauth.navigation.AdminAuthRoute
import com.example.adminguidecreation.GuideCreationUiState
import com.example.adminguidecreation.R
import com.example.adminguidecreation.navigation.GuideCreationRoute
import com.example.adminhome.navigation.AdminHomeRoute
import com.example.adminnavigation.AdminNavigation
import com.example.uitesthiltmanifest.HiltComponentActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
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
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    private fun string(@StringRes stringRes: Int): String = context.getString(stringRes)

    private lateinit var navController: TestNavHostController

    @Before
    fun setup() {
        hiltRule.inject()
        // Some screens contains viewModel with workManager
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val config = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setExecutor(SynchronousExecutor())
            .build()
        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
    }

    @Test
    fun firstScreen_whenUserNotAuthorized_isAuthScreen() = runTest {
        composeTestRule.setContent {
            navController = rememberTestNavController()
            AdminNavigation(
                navController = navController,
                startDestination = AdminAuthRoute
            )
        }

        assertTrue(
            navController.currentBackStackEntry?.destination?.hasRoute<AdminAuthRoute>() ?: false
        )
    }

    @Test
    fun navigateToGuideCreation_fromAdminHomeScreen() {
        composeTestRule.setContent {
            navController = rememberTestNavController()
            AdminNavigation(
                navController = navController,
                startDestination = AdminHomeRoute
            )
        }

        var times = 1
        repeat(2) {
            composeTestRule.onNodeWithText(string(com.example.adminhome.R.string.create_guide_grid_button))
                .performClick()

            assertTrue(
                navController.currentBackStackEntry?.destination?.hasRoute<GuideCreationRoute>()
                    ?: false
            )

            composeTestRule.onNodeWithContentDescription(GuideCreationUiState.TITLE)
                .performTextInput("Test title $times")
            ++times

            composeTestRule
                .onNodeWithContentDescription(GuideCreationUiState.BACK_BUTTON)
                .performClick()

            composeTestRule.onNodeWithText(string(R.string.save))
                .performClick()

            assertTrue(
                navController.currentBackStackEntry?.destination?.hasRoute<AdminHomeRoute>()
                    ?: false
            )
        }
    }

    // TODO
    @Test
    fun firstDraftSaved_afterCreatingGuideCardClicked_withSomeTextInside() {
        composeTestRule.onNodeWithText(string(com.example.adminhome.R.string.create_guide_grid_button))
            .performClick()
    }

    @Composable
    private fun rememberTestNavController(): TestNavHostController {
        val context = LocalContext.current
        return remember {
            TestNavHostController(context).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
        }
    }
}
