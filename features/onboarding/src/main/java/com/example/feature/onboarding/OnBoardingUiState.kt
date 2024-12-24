package com.example.feature.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cringehub.theme.CringeHubTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface OnBoardingUiState {

    fun toHomePage(
        currentPage: Int,
        onHomeRedirect: () -> Unit
    )

    @Composable
    fun Show(
        onHomeRedirect: () -> Unit
    )

    object Navigate : OnBoardingUiState {
        override fun toHomePage(
            currentPage: Int,
            onHomeRedirect: () -> Unit
        ) {
            if (currentPage == 3) {
                onHomeRedirect()
            }
        }

        @Composable
        override fun Show(
            onHomeRedirect: () -> Unit
        ) = Unit
    }

    object Initial : OnBoardingUiState {
        override fun toHomePage(
            currentPage: Int,
            onHomeRedirect: () -> Unit
        ) = Unit

        @Composable
        override fun Show(
            onHomeRedirect: () -> Unit
        ) {
            val items = OnBoardingItems.getData()
            val scope = rememberCoroutineScope()
            val pagerState = rememberPagerState(pageCount = { 4 })

            Column(modifier = Modifier.fillMaxSize()) {
                TopSection(
                    pagerState,
                    onBackClicked = {
                        if (pagerState.currentPage > 0) scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }, onSkipClicked = {
                        if (pagerState.currentPage < items.size - 1) scope.launch {
                            pagerState.animateScrollToPage(items.size - 1)
                        }
                    })

                Box(modifier = Modifier.fillMaxSize()) {
                    HorizontalPager(modifier = Modifier.fillMaxHeight(0.9f), state = pagerState) {
                        OnBoardingItem(items[it])
                    }
                    Indicators(pagerState)
                    NextButton(pagerState, scope, onHomeRedirect)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopSection(
    pagerState: PagerState,
    onBackClicked: () -> Unit,
    onSkipClicked: () -> Unit
) {
    CenterAlignedTopAppBar(
        modifier = Modifier
            .fillMaxHeight(0.1f)
            .fillMaxWidth()
            .padding(CringeHubTheme.dimensions.spaceMedium),
        title = {
            Text(text = "CringeHub")
        },
        navigationIcon = {
            AnimatedVisibility(
                visible = pagerState.currentPage != 0
            ) {
                Button(onClick = onBackClicked) {
                    Text("Back")
                }
            }
        },
        actions = {
            AnimatedVisibility(
                visible = pagerState.currentPage != 3
            ) {
                Button(onClick = onSkipClicked) {
                    Text("Skip")
                }
            }
        },
        windowInsets = WindowInsets.systemBars
            .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top)
    )
}

@Composable
private fun OnBoardingItem(items: OnBoardingItems) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = items.image),
            contentDescription = "Image1",
            modifier = Modifier.padding(horizontal = 48.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(id = items.title),
            style = CringeHubTheme.typography.title,
            // fontSize = 24.sp,
            color = CringeHubTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            letterSpacing = 1.sp,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = items.desc),
            style = CringeHubTheme.typography.title,
            color = CringeHubTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(10.dp),
            letterSpacing = 1.sp,
        )
    }
}

@Composable
private fun Indicators(pagerState: PagerState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color =
                if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(16.dp)
            )
        }
    }
}

@Composable
private fun BoxScope.NextButton(
    pagerState: PagerState,
    scope: CoroutineScope,
    onHomeRedirect: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxHeight(0.1f)
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .padding(CringeHubTheme.dimensions.spaceSmall)
            .semantics {
                contentDescription = "next button"
            },
        onClick = {
            if (pagerState.currentPage < pagerState.pageCount) scope.launch {
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            }
            OnBoardingUiState.Navigate.toHomePage(pagerState.currentPage, onHomeRedirect)
        }
    ) {
        Text("Next")
    }
}
