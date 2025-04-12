package com.example.admin

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.adminauth.navigation.AdminAuthRoute
import com.example.adminguidecreation.GuideCreationScreen
import com.example.adminguidecreation.navigation.GuideCreationRoute
import com.example.adminhome.navigation.AdminHomeRoute
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setup() {
        hiltRule.inject()
        composeTestRule.activity.setContent {
            navController = rememberTestNavController()
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            com.example.adminnavigation.AdminNavigation(
                navController = navController,
                AdminAuthRoute
            )
        }
        with(composeTestRule.onNodeWithContentDescription("AdminHomeScreen")) {
            assertExists()
            isDisplayed()
        }
    }

    // TODO: Hard test to complete
    @Test
    fun firstDraftSaved_afterCreatingGuideCardClicked_withSomeTextInside() {
        composeTestRule.onNodeWithText("GuideDomain creation card").performClick()

        with(composeTestRule.onNodeWithContentDescription("GuideCreationScreen")) {
            assertExists()
            isDisplayed()
        }
        composeTestRule.activity.onBackPressedDispatcher.onBackPressed()

        with(composeTestRule.onNodeWithContentDescription("AdminHomeScreen")) {
            assertExists()
            isDisplayed()

        }
        composeTestRule.onNodeWithTag("Cards column admin actions")
        composeTestRule.onNodeWithText("Draft text").isDisplayed()
//        composeTestRule.onNode(
//            hasText(RallyScreen.Accounts.name.uppercase()) and
//                    hasParent(
//                        hasContentDescription(RallyScreen.Accounts.name)
//                    ), true
//        )
    }

    @Test
    fun firstDraftDeleted_afterDeleteIconOnDraftClicked() {

    }

    @Test
    fun draftNotSaved_afterCreatingGuideCardClicked_contentShouldBeNotEmpty() {

    }
}

@Composable
private fun rememberTestNavController(): TestNavHostController {
    val context = LocalContext.current
    return remember {
        TestNavHostController(context).apply {
            navigatorProvider.addNavigator(ComposeNavigator())
            graph = createGraph(startDestination = AdminHomeRoute) {
                composable<GuideCreationRoute> {
                    GuideCreationScreen()
                }
            }
        }
    }
}