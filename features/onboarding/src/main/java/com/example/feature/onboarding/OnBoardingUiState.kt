package com.example.feature.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsIgnoringVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cringehub.theme.CringeHubTheme
import com.example.cringehub.theme.PrimaryBlack
import com.example.cringehub.theme.PrimaryWhite
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

            val onBoardingCD = stringResource(id = R.string.onboarding_page_cd)
            Column(
                modifier = Modifier
                    .semantics {
                        contentDescription = onBoardingCD
                    }
                    .fillMaxSize()
                    .background(CringeHubTheme.colorScheme.primary)
            ) {
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

                Spacer(modifier = Modifier.padding(vertical = CringeHubTheme.dimensions.spaceLarge))

                Box(modifier = Modifier.fillMaxSize()) {
                    HorizontalPager(
                        modifier = Modifier
                            .wrapContentHeight(),
                        state = pagerState
                    ) {
                        OnBoardingItem(items[it])
                    }
                    Indicators(pagerState)
                    NextButton(pagerState, scope, onHomeRedirect)
                }
            }
        }
    }
}

@Composable
private fun TopSection(
    pagerState: PagerState,
    onBackClicked: () -> Unit,
    onSkipClicked: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth().padding(CringeHubTheme.dimensions.spaceMedium)
    ) {
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.CenterStart),
            visible = pagerState.currentPage != 0
        ) {
            Button(
                modifier = Modifier.semantics {
                    contentDescription = "back onboarding"
                },
                onClick = onBackClicked, colors = ButtonColors(
                    CringeHubTheme.colorScheme.secondary,
                    contentColor = CringeHubTheme.colorScheme.onBackground,
                    disabledContainerColor = CringeHubTheme.colorScheme.onBackground,
                    disabledContentColor = CringeHubTheme.colorScheme.onBackground
                ),
            ) {
                Text(stringResource(R.string.onboarding_back), style = CringeHubTheme.typography.onContainerBody)
            }
        }
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(R.string.main_title),
            style = CringeHubTheme.typography.title
        )
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.CenterEnd),
            visible = pagerState.currentPage != 3
        ) {
            Button(
                modifier = Modifier.semantics {
                    contentDescription = "skip onboarding"
                },
                onClick = onSkipClicked, colors = ButtonColors(
                    CringeHubTheme.colorScheme.secondary,
                    contentColor = CringeHubTheme.colorScheme.onBackground,
                    disabledContainerColor = CringeHubTheme.colorScheme.onBackground,
                    disabledContentColor = CringeHubTheme.colorScheme.onBackground
                )
            ) {
                Text(stringResource(R.string.onboarding_skip), style = CringeHubTheme.typography.onContainerBody)
            }
        }
    }
}

@Composable
private fun OnBoardingItem(items: OnBoardingItems) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = items.image),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 48.dp, end = 48.dp, top = 32.dp, bottom = 24.dp)
                .requiredHeight(300.dp)
        )
        Text(
            text = stringResource(id = items.title),
            style = CringeHubTheme.typography.title,
            color = CringeHubTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.padding(horizontal = CringeHubTheme.dimensions.spaceLarge),
            text = stringResource(id = items.desc),
            style = CringeHubTheme.typography.body,
            color = CringeHubTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
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
                if (pagerState.currentPage == iteration) PrimaryBlack else PrimaryWhite
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .width(24.dp)
                    .clip(CutCornerShape(4.dp))
                    .background(color)
                    .size(4.dp)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun BoxScope.NextButton(
    pagerState: PagerState,
    scope: CoroutineScope,
    onHomeRedirect: () -> Unit
) {
    Button(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .windowInsetsPadding(WindowInsets.navigationBarsIgnoringVisibility)
            .padding(CringeHubTheme.dimensions.spaceMedium),
        colors = ButtonColors(
            CringeHubTheme.colorScheme.secondary,
            contentColor = CringeHubTheme.colorScheme.onBackground,
            disabledContainerColor = CringeHubTheme.colorScheme.onBackground,
            disabledContentColor = CringeHubTheme.colorScheme.onBackground
        ),
        onClick = {
            if (pagerState.currentPage < pagerState.pageCount) scope.launch {
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            }
            OnBoardingUiState.Navigate.toHomePage(pagerState.currentPage, onHomeRedirect)
        }
    ) {
        Text(
            modifier = Modifier.padding(vertical = CringeHubTheme.dimensions.spaceSmall),
            text = stringResource(R.string.onboarding_next),
            style = CringeHubTheme.typography.onContainerBody
        )
    }
}
