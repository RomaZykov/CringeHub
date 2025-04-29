package com.example.adminhome

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.adminhome.model.GuideUi
import com.example.adminhome.model.InitialUi
import com.example.test.BaseComposeTest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AdminHomeScreenTest : BaseComposeTest() {

    private lateinit var navController: TestNavHostController

    private val fakeViewModel = object : AdminHomeViewModel {
        var uiStateFlowToReturn = InitialUi(emptyList())

        override fun adminHomeUiStateFlow(): StateFlow<AdminHomeUiState> {
            return MutableStateFlow(uiStateFlowToReturn)
        }

        override fun init() {
            val drafts = listOf(
                GuideUi(
                    "1",
                    "Title 1",
                    "Long Long Long Long Long Long  Long Long Long Long Long Long Long  Content",
                    isDraft = true,
                    isFree = false
                ),
                GuideUi(
                    "2",
                    "Long Long Long Long Long Long  Long Long Long Long Long Title 2",
                    "Short Content",
                    isDraft = true,
                    isFree = false
                ),
                GuideUi(
                    "3", "Title 3", "", isDraft = true,
                    isFree = false
                )
            )
            uiStateFlowToReturn = InitialUi(drafts)
        }
    }

    @Test
    fun showDraftGuides_whenAnyExist() {
        fakeViewModel.init()
        restorationTester.setContent {
            navController = TestNavHostController(LocalContext.current)
            AdminHomeScreen(
                navController = navController,
                viewModel = fakeViewModel
            )
        }
        composeTestRule.onRoot().printToLog("AdminHomeScreen")

        composeTestRule.onNodeWithText("Черновики")
            .assertExists()
            .assertIsDisplayed()

        composeTestRule.onAllNodes(hasContentDescription("draft"))
            .assertCountEquals(3)
    }

    @Test
    fun doesNotShowDraftGuides_whenNonExist() {
        restorationTester.setContent {
            navController = TestNavHostController(LocalContext.current)
            AdminHomeScreen(
                navController = navController,
                viewModel = fakeViewModel
            )
        }

        composeTestRule.onNodeWithText(string(R.string.drafts))
            .assertDoesNotExist()

        composeTestRule.onAllNodes(hasContentDescription("draft")).assertCountEquals(0)
    }

    @Test
    fun navigateToDraft_whenBySpecificDraftCLicked() {
        fakeViewModel.init()
        restorationTester.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            AdminHomeScreen(
                navController = navController,
                viewModel = fakeViewModel
            )
        }

        composeTestRule.onNodeWithText("Long Long Long Long Long Long  Long Long Long Long Long Title 2")
            .performClick()

    }
}