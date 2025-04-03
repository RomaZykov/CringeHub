package com.example.adminguidecreation

import androidx.activity.ComponentActivity
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.adminguidecreation.model.InitialUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
class GuideCreationScreenTest {

    private class FakeGuideCreationViewModel : GuideCreationViewModel {
        var saveContentCalledCount = 0
        var uiStateFlowToReturn: GuideCreationUiState = InitialUi

        override fun guideCreationUiStateFlow(): StateFlow<GuideCreationUiState> {
            return MutableStateFlow(uiStateFlowToReturn)
        }

        override fun onPublishClicked() {
        }

        override fun saveContent(title: String, content: String) {
            saveContentCalledCount++
        }
    }

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setUp() {
        val fakeViewModel = FakeGuideCreationViewModel()
        composeTestRule.setContent {
            GuideCreationScreen(
                popBackStack = {
                    composeTestRule.activityRule.scenario.onActivity { activity ->
                        activity.onBackPressedDispatcher.onBackPressed()
                    }
                },
                viewModel = fakeViewModel
            )
        }
        composeTestRule.onRoot(true).printToLog("GuideCreationScreenTest")
    }

    @Test
    fun showDialog_whenTitleOrContentNotEmpty() {
        composeTestRule.onNodeWithContentDescription("title")
            .assertExists()
            .assertIsDisplayed()
            .performTextInput("Test title")
        composeTestRule.onNodeWithContentDescription("title")
            .assertTextEquals("Test title")

        composeTestRule.onNodeWithContentDescription("discard changes dialog").assertDoesNotExist()

        composeTestRule
            .onNodeWithContentDescription("back button")
            .performClick()

        composeTestRule.onNodeWithContentDescription("discard changes dialog")
            .assertExists()
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("cancel")
            .assertExists()
            .assertIsDisplayed()
            .performClick()

        composeTestRule.onNodeWithContentDescription(
            "discard changes dialog"
        ).assertDoesNotExist()

        composeTestRule.onNodeWithContentDescription("title")
            .performTextClearance()

        composeTestRule.onNodeWithContentDescription("content")
            .assertExists()
            .assertIsDisplayed()
            .performTextInput("Test content")
        composeTestRule.onNodeWithContentDescription("content")
            .assertTextEquals("Test content")

        composeTestRule.onRoot().performClick()

        composeTestRule.onNodeWithContentDescription("discard changes dialog").assertDoesNotExist()

        composeTestRule.onNodeWithContentDescription("content")
            .performTextClearance()

        assertTextFieldEquals("title", "")
        composeTestRule.onNodeWithContentDescription("title")
            .assertExists()
            .assertIsDisplayed()

        assertTextFieldEquals("content", "")
        composeTestRule.onNodeWithContentDescription("content")
            .assertExists()
            .assertIsDisplayed()

        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }

        composeTestRule.onNodeWithContentDescription("discard changes dialog")
            .assertDoesNotExist()
    }

    @Test
    fun doesNotShowDialog_whenTitleAndContentEmpty() {
        composeTestRule.onNodeWithContentDescription("title")
            .assertExists()
            .assertIsDisplayed()
        assertTextFieldEquals("title", "")

        composeTestRule.onNodeWithContentDescription("content")
            .assertExists()
            .assertIsDisplayed()
        assertTextFieldEquals("content", "")

        composeTestRule.onNodeWithContentDescription("discard changes dialog").assertDoesNotExist()

        composeTestRule.onNodeWithContentDescription("back button")
            .performClick()

        composeTestRule.onNode(isRoot()).assertDoesNotExist()
    }

    private fun assertTextFieldEquals(nodeWithContentDescription: String, expectedResult: String) {
        for ((key, value) in composeTestRule.onNodeWithContentDescription(nodeWithContentDescription)
            .fetchSemanticsNode().config) {
            if (key.name == SemanticsProperties.EditableText.name)
                assertEquals(expectedResult, value.toString())
        }
    }
}