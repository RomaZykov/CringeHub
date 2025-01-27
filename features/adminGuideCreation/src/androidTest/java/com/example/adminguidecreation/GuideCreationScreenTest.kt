package com.example.adminguidecreation

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GuideCreationScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Test
    fun `fdsbfdsbdfsb`() {
        composeTestRule.setContent {
            AdminGui
            BookmarksScreen(
                feedState = NewsFeedUiState.Loading,
                onShowSnackbar = { _, _ -> false },
                removeFromBookmarks = {},
                onTopicClick = {},
                onNewsResourceViewed = {},
            )
        }

        composeTestRule
            .onNodeWithContentDescription(
                composeTestRule.activity.resources.getString(R.string.feature_bookmarks_loading),
            )
            .assertExists()
    }

//    @Test
//    fun feed_whenHasBookmarks_showsBookmarks() {
//        composeTestRule.setContent {
//            BookmarksScreen(
//                feedState = NewsFeedUiState.Success(
//                    userNewsResourcesTestData.take(2),
//                ),
//                onShowSnackbar = { _, _ -> false },
//                removeFromBookmarks = {},
//                onTopicClick = {},
//                onNewsResourceViewed = {},
//            )
//        }
//
//        composeTestRule
//            .onNodeWithText(
//                userNewsResourcesTestData[0].title,
//                substring = true,
//            )
//            .assertExists()
//            .assertHasClickAction()
//
//        composeTestRule.onNode(hasScrollToNodeAction())
//            .performScrollToNode(
//                hasText(
//                    userNewsResourcesTestData[1].title,
//                    substring = true,
//                ),
//            )
//
//        composeTestRule
//            .onNodeWithText(
//                userNewsResourcesTestData[1].title,
//                substring = true,
//            )
//            .assertExists()
//            .assertHasClickAction()
//    }
//
//    @Test
//    fun feed_whenRemovingBookmark_removesBookmark() {
//        var removeFromBookmarksCalled = false
//
//        composeTestRule.setContent {
//            BookmarksScreen(
//                feedState = NewsFeedUiState.Success(
//                    userNewsResourcesTestData.take(2),
//                ),
//                onShowSnackbar = { _, _ -> false },
//                removeFromBookmarks = { newsResourceId ->
//                    kotlin.test.assertEquals(userNewsResourcesTestData[0].id, newsResourceId)
//                    removeFromBookmarksCalled = true
//                },
//                onTopicClick = {},
//                onNewsResourceViewed = {},
//            )
//        }
//
//        composeTestRule
//            .onAllNodesWithContentDescription(
//                composeTestRule.activity.getString(
//                    com.google.samples.apps.nowinandroid.core.ui.R.string.core_ui_unbookmark,
//                ),
//            ).filter(
//                hasAnyAncestor(
//                    hasText(
//                        userNewsResourcesTestData[0].title,
//                        substring = true,
//                    ),
//                ),
//            )
//            .assertCountEquals(1)
//            .onFirst()
//            .performClick()
//
//        kotlin.test.assertTrue(removeFromBookmarksCalled)
//    }
}