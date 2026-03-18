package com.example.adminGuideCreation

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasImeAction
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.text.input.ImeAction
import com.example.adminGuideCreation.components.ContentItem
import com.example.test.core.StringResources

class GuideCreationPage(
    private val composeTestRule: ComposeTestRule
) : StringResources() {

    private val addNewPageButton =
        composeTestRule.onNodeWithContentDescription(GuideCreationUiState.CREATE_PAGE_BUTTON)

    private var currentParIndex = 0
    private var currentPageIndex = 0

    fun assertContentLabelDisplayed() {
        composeTestRule.onNodeWithContentDescription(
            "0 ${ContentItem.TYPE_TEXT}",
            substring = true
        ).assertTextEquals(retrieveString(R.string.content))
    }

    fun performTextByParagraphIndex(i: Int, content: String) {
        currentParIndex = i
        composeTestRule.onNodeWithContentDescription(
            "$i ${ContentItem.TYPE_TEXT}",
            substring = true
        ).performTextInput(content)
    }

    fun assertTextByParagraphIndexEquals(i: Int, content: String) {
        composeTestRule.onNodeWithContentDescription(
            "$i ${ContentItem.TYPE_TEXT}",
            substring = true
        ).assertExists().assertIsDisplayed().assertTextEquals(content)
    }

    fun performTransitionToNextParagraph() {
        composeTestRule.onNodeWithContentDescription(
            "$currentParIndex ${ContentItem.TYPE_TEXT}",
            substring = true
        ).assert(hasImeAction(ImeAction.Next)).performClick()
    }

    fun assertPageTitleDisplayedByPageIndex(i: Int) {
        composeTestRule.onNodeWithContentDescription(
            retrieveString(R.string.guide_page, i),
            substring = true
        ).assertExists().assertIsDisplayed()
    }

    fun assertPageNumberNotDisplayedByPageIndex(i: Int) {
        composeTestRule.onNodeWithContentDescription(
            retrieveString(R.string.guide_page, i),
            substring = true
        ).assertIsNotDisplayed().assertDoesNotExist()
    }

    fun performAddNewPageButtonClick() {
        addNewPageButton.performClick()
        composeTestRule.waitForIdle()
    }

    fun assertAddNewPageButtonDisplayed() {
        addNewPageButton.performScrollTo().assertExists().assertIsDisplayed()
    }
}
