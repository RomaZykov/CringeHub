package com.example.adminguidecreation

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.adminguidecreation.model.InitialUi
import com.example.test.BaseComposeTest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
class GuideCreationScreenTest : BaseComposeTest() {

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

//    private lateinit var restorationTester: StateRestorationTester

    @Before
    fun setUp() {
        val fakeViewModel = FakeGuideCreationViewModel()
//        restorationTester = StateRestorationTester(composeTestRule)
        restorationTester.setContent {
            GuideCreationScreen(
                popBackStack = {
                    composeTestRule.activityRule.scenario.onActivity { activity ->
                        activity.onBackPressedDispatcher.onBackPressed()
                    }
                },
                viewModel = fakeViewModel
            )
        }
        composeTestRule.onRoot(true).printToLog("GuideCreationScreenTag")
    }

//    @Test
//    fun transformAllTextToSeveralOptions_whenOnSameLine() {
//        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.CONTENT)
//            .performTextInput("Short 1 paragraph\nLong Long Long Long Long Long 2 paragraph\nShort 3 paragraph")
//
//        composeTestRule.onNodeWithContentDescription(
//            GuideCreationUiState.BOLD_BUTTON,
//            useUnmergedTree = true
//        ).performClick()
//
//        // H1
//        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.CONTENT)
//            .assertExists()
//            .assertIsDisplayed()
//
//        // H2
//
//        // Quote
//    }
//
//    @Test
//    fun undoTransformingAllTextFromSeveralOptions_whenOnSameLine() {
//        // H1
//
//        // H2
//
//        // Quote
//    }
//
//    @Test
//    fun showDialog_whenTitleOrContentNotEmpty_andBackButtonPressed() {
//
//    }

    @Test
    fun showDialog_whenTitleOrContentNotEmpty() {
        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.TITLE)
            .assertExists()
            .assertIsDisplayed()
            .performTextInput("Test title")
        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.TITLE)
            .assertTextEquals("Test title")

        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.DIALOG)
            .assertDoesNotExist()

        composeTestRule
            .onNodeWithContentDescription(GuideCreationUiState.BACK_BUTTON)
            .performClick()

        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.DIALOG)
            .assertExists()
            .assertIsDisplayed()

        // Orientation changes check
        restorationTester.emulateSavedInstanceStateRestore()
        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.DIALOG)
            .assertIsDisplayed()
            .assertExists()

        composeTestRule.onNodeWithText(string(R.string.save))
            .assertExists()
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(string(R.string.cancel))
            .assertExists()
            .assertIsDisplayed()
            .performClick()

        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.DIALOG)
            .isNotDisplayed()

        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.TITLE)
            .performTextClearance()

        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.CONTENT)
            .assertExists()
            .assertIsDisplayed()
            .performTextInput("Test content")

        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.BACK_BUTTON)
            .performClick()

        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.DIALOG)
            .assertIsDisplayed()
            .assertExists()

        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.CONTENT)
            .performTextClearance()

        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }

        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.DIALOG)
            .assertDoesNotExist()
    }

    @Test
    fun doNotShowDialog_whenTitleAndContentEmpty() {
        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.TITLE)
            .assertExists()
            .assertIsDisplayed()
        assertTextFieldEquals(GuideCreationUiState.TITLE, "")

        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.CONTENT)
            .assertExists()
            .assertIsDisplayed()
        assertTextFieldEquals(GuideCreationUiState.CONTENT, "")

        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.DIALOG)
            .assertDoesNotExist()

        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.BACK_BUTTON)
            .performClick()

        composeTestRule.onNode(isRoot()).assertDoesNotExist()
    }

    private fun assertTextFieldEquals(nodeWithContentDescription: String, expectedResult: String) {
        val actualText = composeTestRule.onNodeWithContentDescription(nodeWithContentDescription)
            .fetchSemanticsNode().config[SemanticsProperties.EditableText].text
        assertEquals(expectedResult, actualText)
    }
}