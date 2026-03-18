package com.example.adminguidecreation

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasImeAction
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.text.input.ImeAction
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.adminGuideCreation.GuideCreationPage
import com.example.adminGuideCreation.GuideCreationScreenUi
import com.example.adminGuideCreation.GuideCreationUiState
import com.example.adminGuideCreation.R
import com.example.adminGuideCreation.components.ContentItem
import com.example.adminGuideCreation.model.EditableGuideUi
import com.example.test.BaseComposeTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals

@RunWith(AndroidJUnit4::class)
class GuideCreationScreenTest : BaseComposeTest() {

    private lateinit var guideCreationPage: GuideCreationPage

    @Before
    fun setUp() {
        val uiState = EditableGuideUi(
            guideId = "",
            title = "",
        )
        restorationTester.setContent {
            GuideCreationScreenUi(
                popBackStack = {
                    composeTestRule.activityRule.scenario.onActivity { activity ->
                        activity.onBackPressedDispatcher.onBackPressed()
                    }
                },
                uiState = uiState,
                onSaveContent = {},
                onPublishClicked = {},
            )
        }
    }

    @Test
    fun showImagesCorrectly_withSomeTextBetweenParagraphs() {
        var count = 0
        repeat(5) {
            composeTestRule.onNodeWithContentDescription(
                "$count ${ContentItem.TYPE_TEXT}",
                substring = true
            ).performTextInput("$count paragraph")

            composeTestRule.onNodeWithContentDescription(
                GuideCreationUiState.IMAGE_BUTTON,
                useUnmergedTree = true
            ).performClick()

            // Add image from gallery
        }

        composeTestRule.onAllNodesWithContentDescription(ContentItem.TYPE_IMAGE, substring = true)
            .assertCountEquals(5)
    }

    @Test
    fun showLongTextInContentSide_withSeveralParagraphs() {
        guideCreationPage.assertContentLabelDisplayed()
        guideCreationPage.performTextByParagraphIndex(0, superLongTextInParagraph)
        guideCreationPage.assertTextByParagraphIndexEquals(0, superLongTextInParagraph)

        restorationTester.emulateSavedInstanceStateRestore()

        guideCreationPage.assertTextByParagraphIndexEquals(0, superLongTextInParagraph)
        guideCreationPage.performTransitionToNextParagraph()
        guideCreationPage.assertTextByParagraphIndexEquals(1, "")
    }

    @Test
    fun createSeveralPagesWithContent() {
        guideCreationPage.assertPageTitleDisplayedByPageIndex(0)
        guideCreationPage.assertContentLabelDisplayed()
        guideCreationPage.assertAddNewPageButtonDisplayed()

        // First paragraph
        guideCreationPage.performTextByParagraphIndex(0,"First paragraph on first page")
        guideCreationPage.assertTextByParagraphIndexEquals(0,"First paragraph on first page")

        // Add second paragraph as image
        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.IMAGE_BUTTON)
            .performClick()
        composeTestRule
            .onNode(
                hasContentDescription(
                    "1 ${ContentItem.TYPE_IMAGE}",
                    substring = true
                ) and
                        hasParent(
                            hasContentDescription(retrieveString(R.string.guide_page, 1))
                        ),
                useUnmergedTree = true
            ).assertExists().assertIsDisplayed()

        // Second page of content
        composeTestRule.onNodeWithContentDescription(
            retrieveString(R.string.guide_page, 1),
            substring = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.CREATE_PAGE_BUTTON)
            .assertExists().assertIsDisplayed().performClick()
        composeTestRule.onNodeWithContentDescription(
            retrieveString(R.string.guide_page, 1),
            substring = true
        ).assertExists().assertIsDisplayed()

        with(
            composeTestRule
                .onNode(
                    hasContentDescription(
                        "0 ${ContentItem.TYPE_TEXT}",
                        substring = true
                    ) and
                            hasParent(
                                hasContentDescription(retrieveString(R.string.guide_page, 1))
                            ),
                    useUnmergedTree = true
                )
        ) {
            assertExists()

            assertTextEquals("Content", includeEditableText = false)

            performTextInput("First paragraph on second page")
            assertTextEquals("First paragraph on second page")
        }
        // Add second paragraph as image
        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.IMAGE_BUTTON)
            .performClick()
        composeTestRule
            .onNode(
                hasContentDescription(
                    "1 ${ContentItem.TYPE_IMAGE}",
                    substring = true
                ) and
                        hasParent(
                            hasContentDescription(retrieveString(R.string.guide_page, 1))
                        ),
                useUnmergedTree = true
            ).assertExists().assertIsDisplayed()

        // Third page of content
        composeTestRule.onNodeWithContentDescription(
            retrieveString(R.string.guide_page, 2),
            substring = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.CREATE_PAGE_BUTTON)
            .assertExists().assertIsDisplayed().performClick()
        composeTestRule.onNodeWithContentDescription(
            retrieveString(R.string.guide_page, 2),
            substring = true
        ).assertExists().assertIsDisplayed()

        with(
            composeTestRule
                .onNode(
                    hasContentDescription(
                        "0 ${ContentItem.TYPE_TEXT}",
                        substring = true
                    ) and
                            hasParent(
                                hasContentDescription(retrieveString(R.string.guide_page, 2))
                            ),
                    useUnmergedTree = true
                )
        ) {
            assertExists()

            assertTextEquals("Content", includeEditableText = false)

            performTextInput("First paragraph on third page")
            assertTextEquals("First paragraph on third page")

            assert(hasImeAction(ImeAction.Next)).performClick()
        }
        with(
            composeTestRule
                .onNode(
                    hasContentDescription(
                        "1 ${ContentItem.TYPE_TEXT}",
                        substring = true
                    ) and
                            hasParent(
                                hasContentDescription(retrieveString(R.string.guide_page, 2))
                            ),
                    useUnmergedTree = true
                )
        ) {
            assertExists()

            performTextInput("Second paragraph on third page")
            assertTextEquals("Second paragraph on third page")
        }
    }

    @Test // Done - reworked
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

        composeTestRule.onNodeWithText(retrieveString(R.string.save))
            .assertExists()
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(retrieveString(R.string.cancel))
            .assertExists()
            .assertIsDisplayed()
            .performClick()

        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.DIALOG)
            .isNotDisplayed()

        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.TITLE)
            .performTextClearance()

        // First empty paragraph
        composeTestRule.onNodeWithContentDescription("0 ${ContentItem.TYPE_TEXT}", substring = true)
            .assertExists()
            .assertIsDisplayed()
            .performTextInput("Test content in 1 paragraph")

        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.BACK_BUTTON)
            .performClick()

        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.DIALOG)
            .assertIsDisplayed()
            .assertExists()

        composeTestRule.onNodeWithContentDescription("0 ${ContentItem.TYPE_TEXT}", substring = true)
            .performTextClearance()

        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }

        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.DIALOG)
            .assertDoesNotExist()
    }

    @Test // Done - reworked
    fun doNotShowDialog_whenTitleAndContentEmpty() {
        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.TITLE)
            .assertExists()
            .assertIsDisplayed()
        assertTextFieldEquals(GuideCreationUiState.TITLE, "")

        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.CONTENT)
            .assertExists()
            .assertIsDisplayed()

        assertTextFieldEquals("0 ${ContentItem.TYPE_TEXT}", "")

        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.DIALOG)
            .assertDoesNotExist()

        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.BACK_BUTTON)
            .performClick()

        composeTestRule.onNode(isRoot()).assertDoesNotExist()
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

    @Test
    fun performStyleTransformation_inDifferentTextParagraphs() {
        // H1

        // H2

        // Quote
    }

    private fun assertTextFieldEquals(nodeWithContentDescription: String, expectedResult: String) {
        val actualText = composeTestRule.onNodeWithContentDescription(
            nodeWithContentDescription,
            substring = true
        ).fetchSemanticsNode().config[SemanticsProperties.InputText]
        assertEquals(expectedResult, actualText.text)
    }

    private val superLongTextInParagraph =
        "SuperLongFirstParagraphSuperLongFirstParagraphSuperLongFirstParagraphSuperLongFirstParagraphSuperLongFirstParagraphSuperLongFirstParagraphSuperLongFirstParagraphSuperLongFirstParagraphSuperLongFirstParagraphSuperLongFirstParagraphSuperLongFirstParagraphSuperLongFirstParagraph"
}
