package com.example.adminGuideCreation

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.adminGuideCreation.components.ContentItem
import com.example.adminGuideCreation.model.EditableGuideUi
import com.example.test.BaseComposeTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

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
        repeat(5) {
            guideCreationPage.performTextByParagraphAndPage(0, it, "${it + 1} paragraph")

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
        guideCreationPage.assertContentLabelByPageDisplayed(1)
        guideCreationPage.performTextByParagraphAndPage(1, 1, superLongTextInParagraph)
        guideCreationPage.assertTextByParagraphAndPageEquals(1, 1, superLongTextInParagraph)

        restorationTester.emulateSavedInstanceStateRestore()

        guideCreationPage.assertTextByParagraphAndPageEquals(1, 1, superLongTextInParagraph)
        guideCreationPage.performTransitionToNextParagraphByPageAndParagraph(1, 1)
        guideCreationPage.assertTextByParagraphAndPageEquals(1, 2, "")
    }

    @Test
    fun showDialog_whenTitleOrContentNotEmpty() {
        guideCreationPage.assertEmptyTitleDisplayed()
        guideCreationPage.performTitleText("Test title")
        guideCreationPage.assertTitleTextEquals("Test title")
        guideCreationPage.SaveDialog().assertNotDisplayed()
        guideCreationPage.performBackButtonClick()
        guideCreationPage.SaveDialog().assertDisplayed()

        restorationTester.emulateSavedInstanceStateRestore()

        guideCreationPage.SaveDialog().assertDisplayed()
        guideCreationPage.SaveDialog().assertSaveAndCancelButtonDisplayed()
        guideCreationPage.SaveDialog().performCancelButtonClick()
        guideCreationPage.SaveDialog().assertNotDisplayed()
        guideCreationPage.performTitleTextClearing()

        // First empty paragraph
        guideCreationPage.assertContentLabelByPageDisplayed(1)
        guideCreationPage.performTextByParagraphAndPage(1, 1, "Test content in 1 paragraph")
        guideCreationPage.performBackButtonClick()
        guideCreationPage.SaveDialog().assertDisplayed()
        guideCreationPage.performTextClearingByPageAndParagraph(1, 1)

        composeTestRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }

        guideCreationPage.SaveDialog().assertNotDisplayed()
    }

    @Test
    fun createSeveralPagesWithContent() {
        guideCreationPage.assertPageNumberDisplayed(1)
        guideCreationPage.assertContentLabelByPageDisplayed(1)
        guideCreationPage.assertAddNewPageButtonDisplayed()

        // First paragraph
        guideCreationPage.performTextByParagraphAndPage(1, 1, "First paragraph on first page")
        guideCreationPage.assertTextByParagraphAndPageEquals(
            1,
            1,
            "First paragraph on first page"
        )

        // Add second paragraph as image
        guideCreationPage.performAddNewPageButtonClick()
        guideCreationPage.assertPageNumberDisplayed(2)
//        composeTestRule
//            .onNode(
//                hasContentDescription(
//                    "1 ${ContentItem.TYPE_IMAGE}",
//                    substring = true
//                ) and
//                        hasParent(
//                            hasContentDescription(retrieveString(R.string.guide_page, 1))
//                        ),
//                useUnmergedTree = true
//            ).assertExists().assertIsDisplayed()

        // Second page of content
        guideCreationPage.assertPageNumberNotDisplayed(2)
        guideCreationPage.performAddNewPageButtonClick()
        guideCreationPage.assertPageNumberDisplayed(2)
        guideCreationPage.assertContentLabelByPageDisplayed(2)
        guideCreationPage.performTextByParagraphAndPage(2, 1, "First paragraph on second page")
        guideCreationPage.assertTextByParagraphAndPageEquals(2, 1, "First paragraph on second page")

        // Add second paragraph as image
        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.IMAGE_BUTTON)
            .performClick()
//        composeTestRule
//            .onNode(
//                hasContentDescription(
//                    "1 ${ContentItem.TYPE_IMAGE}",
//                    substring = true
//                ) and
//                        hasParent(
//                            hasContentDescription(retrieveString(R.string.guide_page, 1))
//                        ),
//                useUnmergedTree = true
//            ).assertExists().assertIsDisplayed()

        // Third page of content
        guideCreationPage.assertPageNumberNotDisplayed(3)
        guideCreationPage.performAddNewPageButtonClick()
        guideCreationPage.assertPageNumberDisplayed(3)
        guideCreationPage.assertContentLabelByPageDisplayed(3)
        guideCreationPage.performTextByParagraphAndPage(3, 1, "First paragraph on third page")
        guideCreationPage.assertTextByParagraphAndPageEquals(3, 1, "First paragraph on third page")
        guideCreationPage.performTransitionToNextParagraphByPageAndParagraph(3, 1)
        guideCreationPage.performTextByParagraphAndPage(3, 2, "Second paragraph on third page")
        guideCreationPage.assertTextByParagraphAndPageEquals(3, 2, "Second paragraph on third page")
    }

    @Test
    fun doNotShowDialog_whenTitleAndContentEmpty() {
        guideCreationPage.assertEmptyTitleDisplayed()
        guideCreationPage.assertTitleTextEquals("")

        guideCreationPage.assertContentLabelByPageDisplayed(1)
        guideCreationPage.assertTextByParagraphAndPageEquals(1, 1, "")
        guideCreationPage.SaveDialog().assertNotDisplayed()
        guideCreationPage.performBackButtonClick()

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

//    @Test
//    fun performStyleTransformation_inDifferentTextParagraphs() {
//        // H1
//
//        // H2
//
//        // Quote
//    }

    private val superLongTextInParagraph =
        "SuperLongFirstParagraphSuperLongFirstParagraphSuperLongFirstParagraphSuperLongFirstParagraphSuperLongFirstParagraphSuperLongFirstParagraphSuperLongFirstParagraphSuperLongFirstParagraphSuperLongFirstParagraphSuperLongFirstParagraphSuperLongFirstParagraphSuperLongFirstParagraph"
}
