package com.example.adminhome

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AdminHomeScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

//            BookmarksScreen(
//                feedState = NewsFeedUiState.Loading,
//                onShowSnackbar = { _, _ -> false },
//                removeFromBookmarks = {},
//                onTopicClick = {},
//                onNewsResourceViewed = {},
//            )

//    @Test
//    fun draftSaved_afterCreatingGuide()
//    {
//        composeTestRule.setContent {
//            AdminHomeScreen()
//        }
//    }
}