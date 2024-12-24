package com.example.onboarding

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeRight
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

    // matcher based on the click label, example
//    fun hasClickLabel(label: String) = SemanticsMatcher("Clickable action with label: $label") {
//        it.config.getOrNull(
//            SemanticsActions.OnClick
//        )?.label == label
//    }

    @Before
    fun setUp() {
        composeTestRule.setContent {
            CringeHubTheme {
                OnBoardingScreen(OnBoardingUiState.Initial) {}
            }
        }
    }

    @Test
    fun onBoardingPager_whenDifferentSelectionUsed_showsEachPagesFromStartToEnd() {
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.resources.getString(R.string.onboarding_button_cd))
            .assertExists()
        composeTestRule.onNodeWithText(composeTestRule.activity.resources.getString(R.string.onboarding_next))
            .assertIsDisplayed()

        repeat(2) {
            composeTestRule.onNodeWithContentDescription("next onboarding page").performClick()
        }
        repeat(2) {
            composeTestRule.onNodeWithContentDescription("next onboarding page")
                .performTouchInput { swipeRight() }
        }
    }

    @Test
    fun onBoardingPager_whenSkipButtonUsed_moveToEnd() {
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.resources.getString(R.string.onboarding_button_cd))
            .assertExists()
        composeTestRule.onNodeWithText(composeTestRule.activity.resources.getString(R.string.onboarding_next))
            .assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription("skip onboarding")
            .performTouchInput { swipeRight() }
    }

    @Test
    fun onBoardingPager_whenBackButtonUsed_moveFromEndToStart() {
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.resources.getString(R.string.onboarding_button_cd))
            .assertExists()
        composeTestRule.onNodeWithText(composeTestRule.activity.resources.getString(R.string.onboarding_next))
            .assertIsDisplayed()

        repeat(4) {
            composeTestRule.onNodeWithContentDescription("next onboarding page").performClick()
        }
        repeat(4) {
            composeTestRule.activity.onBackPressedDispatcher.onBackPressed()
        }
    }
}