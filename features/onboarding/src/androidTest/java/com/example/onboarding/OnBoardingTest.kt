package com.example.onboarding

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cringehub.theme.CringeHubTheme
import com.example.feature.onboarding.OnBoardingScreen
import com.example.feature.onboarding.OnBoardingUiState
import com.example.feature.onboarding.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OnBoardingTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            CringeHubTheme {
                OnBoardingScreen(OnBoardingUiState.Initial) {}
            }
        }
        with(
            composeTestRule.onNodeWithContentDescription(
                composeTestRule.activity.resources.getString(
                    R.string.onboarding_page_cd
                )
            )
        ) {
            assertExists()
            assertIsDisplayed()
        }
    }

    @Test
    fun onBoardingPager_whenDifferentSelectionUsed_showsEachPagesFromStartToEnd() {
        repeat(1) {
            composeTestRule.onNodeWithText(
                composeTestRule.activity.resources.getString(
                    R.string.onboarding_next
                )
            ).performClick()
        }
        composeTestRule.onNodeWithText(
            composeTestRule.activity.resources.getString(R.string.onboarding_title_2)
        ).assertIsDisplayed()

        repeat(2) {
            composeTestRule.onNodeWithContentDescription(
                composeTestRule.activity.resources.getString(
                    R.string.onboarding_page_cd
                )
            )
                .performTouchInput { swipeLeft() }
        }
        composeTestRule.onNodeWithText(composeTestRule.activity.resources.getString(R.string.onboarding_title_4))
            .assertIsDisplayed()
    }

    @Test
    fun onBoardingPager_whenSkipButtonUsed_moveToEnd() {
        with(composeTestRule.onNodeWithText(composeTestRule.activity.resources.getString(R.string.onboarding_skip))) {
            assertIsDisplayed()
            performClick()
        }

        composeTestRule.onNodeWithText(composeTestRule.activity.resources.getString(R.string.onboarding_title_4))
            .assertIsDisplayed()
    }

    @Test
    fun onBoardingPager_whenBackButtonUsed_moveFromEndToStart() {
        repeat(3) {
            composeTestRule.onNodeWithText(
                composeTestRule.activity.resources.getString(
                    R.string.onboarding_next
                )
            ).performClick()
        }
        composeTestRule.onNodeWithText(composeTestRule.activity.resources.getString(R.string.onboarding_title_4))
            .assertIsDisplayed()

        repeat(3) {
            composeTestRule.onNodeWithText(composeTestRule.activity.resources.getString(R.string.onboarding_back))
                .performClick()
        }
        composeTestRule.onNodeWithText(composeTestRule.activity.resources.getString(R.string.onboarding_title_1))
            .assertIsDisplayed()
    }

    @Test
    fun onBoardingPager_onFirstPage_doNotShowBackButtonButShowSkipButton() {
        composeTestRule.onNodeWithText(composeTestRule.activity.resources.getString(R.string.onboarding_back))
            .assertIsNotDisplayed()
    }

    @Test
    fun onBoardingPager_onLastPage_doNotShowSkipButtonButShowBackButton() {
        repeat(3) {
            composeTestRule.onNodeWithText(composeTestRule.activity.resources.getString(R.string.onboarding_next))
                .performClick()
        }
        composeTestRule.onNodeWithText(composeTestRule.activity.resources.getString(R.string.onboarding_skip))
            .assertIsNotDisplayed()
    }
}