package com.example.adminGuideCreation

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasImeAction
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.text.input.ImeAction
import com.example.adminGuideCreation.components.ContentItem
import com.example.test.core.StringResources

class GuideCreationPage(
    private val composeTestRule: ComposeTestRule
) : StringResources() {
    private val addNewPageButton =
        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.CREATE_PAGE_BUTTON)
    private val backButton =
        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.BACK_BUTTON)
    private val title =
        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.TITLE)

    private var currentParIndex = 0
    private var currentPageIndex = 0

    inner class SaveDialog() {
        private val saveDialog =
            composeTestRule.onNodeWithContentDescription(GuideCreationUiState.DIALOG)
        private val saveButton =
            composeTestRule.onNodeWithText(retrieveString(R.string.save))
        private val cancelButton =
            composeTestRule.onNodeWithContentDescription(retrieveString(R.string.cancel))

        fun assertSaveAndCancelButtonDisplayed() {
            saveButton.assertExists().assertIsDisplayed()
            cancelButton.assertExists().assertIsDisplayed()
        }

        fun performSaveButtonClick() {
            saveButton.performClick()
            composeTestRule.waitForIdle()
        }

        fun performCancelButtonClick() {
            cancelButton.performClick()
            composeTestRule.waitForIdle()
        }

        fun assertNotDisplayed() {
            saveDialog.assertDoesNotExist()
        }

        fun assertDisplayed() {
            saveDialog.assertExists().assertIsDisplayed()
        }
    }

    fun assertEmptyTitleDisplayed() {
        title.assertExists().assertIsDisplayed()
        title.assertTextEquals("")
    }

    fun performTitleText(text: String) {
        title.performTextInput(text)
    }

    fun performTitleTextClearing() {
        title.performTextClearance()
    }

    fun assertTitleTextEquals(text: String) {
        title.assertTextEquals(text)
    }

    fun assertContentLabelByPageDisplayed(page: Int) {
        composeTestRule
            .onNode(
                hasContentDescription(
                    "0 ${ContentItem.TYPE_TEXT}",
                    substring = true
                ) and hasParent(
                    hasContentDescription(retrieveString(R.string.guide_page, page - 1))
                ),
                useUnmergedTree = true
            ).assertExists().assertIsDisplayed().assertTextEquals(retrieveString(R.string.content))
    }

    fun assertTextByParagraphAndPageEquals(page: Int, paragraph: Int, content: String) {
        composeTestRule
            .onNode(
                hasContentDescription(
                    "${paragraph - 1} ${ContentItem.TYPE_TEXT}",
                    substring = true
                ) and hasParent(
                    hasContentDescription(retrieveString(R.string.guide_page, page - 1))
                ),
                useUnmergedTree = true
            ).assertExists().assertIsDisplayed().assertTextEquals(content)
    }

    fun performTextByParagraphAndPage(page: Int, paragraph: Int, content: String) {
        currentPageIndex = page - 1
        currentParIndex = paragraph - 1
        composeTestRule
            .onNode(
                hasContentDescription(
                    "$currentParIndex ${ContentItem.TYPE_TEXT}",
                    substring = true
                ) and hasParent(
                    hasContentDescription(retrieveString(R.string.guide_page, currentPageIndex))
                ),
                useUnmergedTree = true
            ).performTextInput(content)
    }

    fun assertPageNumberDisplayed(number: Int) {
        composeTestRule.onNodeWithContentDescription(
            retrieveString(R.string.guide_page, number - 1),
            substring = true
        ).assertExists().assertIsDisplayed()
    }

    fun assertPageNumberNotDisplayed(number: Int) {
        composeTestRule.onNodeWithContentDescription(
            retrieveString(R.string.guide_page, number - 1),
            substring = true
        ).assertIsNotDisplayed().assertDoesNotExist()
    }

    fun assertAddNewPageButtonDisplayed() {
        addNewPageButton.performScrollTo().assertExists().assertIsDisplayed()
    }

    fun performAddNewPageButtonClick() {
        addNewPageButton.performScrollTo().performClick()
        composeTestRule.waitForIdle()
    }

    fun performBackButtonClick() {
        backButton.performClick()
        composeTestRule.waitForIdle()
    }

    fun performTransitionToNextParagraphByPageAndParagraph(page: Int, paragraph: Int) {
        currentPageIndex = page - 1
        currentParIndex = paragraph - 1
        composeTestRule
            .onNode(
                hasContentDescription(
                    "$currentParIndex ${ContentItem.TYPE_TEXT}",
                    substring = true
                ) and hasParent(
                    hasContentDescription(retrieveString(R.string.guide_page, currentPageIndex))
                ),
                useUnmergedTree = true
            ).assert(hasImeAction(ImeAction.Next)).performClick()
    }

    fun performTextClearingByPageAndParagraph(page: Int, paragraph: Int) {
        currentPageIndex = page - 1
        currentParIndex = paragraph - 1
        composeTestRule
            .onNode(
                hasContentDescription(
                    "$currentParIndex ${ContentItem.TYPE_TEXT}",
                    substring = true
                ) and hasParent(
                    hasContentDescription(retrieveString(R.string.guide_page, currentPageIndex))
                ),
                useUnmergedTree = true
            ).performScrollTo().performTextClearance()
    }
}
