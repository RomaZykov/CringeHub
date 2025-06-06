package com.example.adminhome.model

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adminhome.AdminHomeUiState
import com.example.adminhome.R
import com.example.adminhome.components.Draft
import com.example.cringehub.theme.CringeHubTheme

data class InitialUi(val allGuides: List<GuideUi>) : AdminHomeUiState {

    @Composable
    override fun Show(
        initWhenAnyGuidesExist: () -> Unit,
        onGuideCreationClicked: () -> Unit,
        onDraftClicked: (String) -> Unit,
        onDraftDeleted: (String) -> Unit
    ) {
        LaunchedEffect(allGuides.isNotEmpty()) {
            initWhenAnyGuidesExist.invoke()
        }
        Column(modifier = Modifier.statusBarsPadding()) {
            NavigationItems(onGuideCreationClicked)
            Drafts(allGuides.filter { it.isDraft }, onDraftClicked, onDraftDeleted)
        }
    }
}

@Composable
private fun Drafts(
    drafts: List<GuideUi>,
    onDraftClicked: (String) -> Unit,
    onDraftDeleted: (String) -> Unit
) {
    if (drafts.isNotEmpty()) {
        Column(modifier = Modifier.padding(CringeHubTheme.dimensions.medium)) {
            Text(text = stringResource(R.string.drafts), style = CringeHubTheme.typography.title)
            LazyColumn(modifier = Modifier.semantics {
                contentDescription = AdminHomeUiState.DRAFT_LIST
            }) {
                drafts.forEach {
                    item {
                        Draft(it, onDraftClicked, onDraftDeleted)
                    }
                }
            }
        }
    }
}

@Composable
private fun NavigationItems(onGuideCreationClicked: () -> Unit) {
    val items = listOf(
        CardItem(
            stringResource(R.string.create_guide_grid_button),
            image = com.example.ui.R.drawable.placeholder_medium_icon,
            onClicked = onGuideCreationClicked
        ),
        CardItem(
            stringResource(R.string.create_practical_task_grid_button),
            image = com.example.ui.R.drawable.placeholder_medium_icon,
            onClicked = {}
        )
    )
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        itemsIndexed(items) { index, item ->
            CardInGrid(index, item)
        }
    }
}

@Composable
private fun CardInGrid(index: Int, item: CardItem) {
    Card(
        modifier = Modifier
            .padding(
                top = CringeHubTheme.dimensions.medium,
                bottom = CringeHubTheme.dimensions.small,
                start = if (index % 2 == 0) CringeHubTheme.dimensions.medium else CringeHubTheme.dimensions.small,
                end = if (index % 2 == 0) CringeHubTheme.dimensions.small else CringeHubTheme.dimensions.medium
            )
            .heightIn(172.dp, 172.dp)
            .clickable {
                item.onClicked.invoke()
            }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(CringeHubTheme.dimensions.medium),
                text = item.title,
                fontSize = 16.sp
            )
            Image(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(CringeHubTheme.dimensions.medium),
                painter = painterResource(item.image),
                contentDescription = null
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AdminHomePreview() {
    val drafts = listOf(
        GuideUi(
            "1",
            "Title 1",
            "Long Long Long Long Long Long  Long Long Long Long Long Long Long  Content",
            false,
            true
        ),
        GuideUi(
            "2",
            "Long Long Long Long Long Long  Long Long Long Long Long Title 2",
            "Short Content",
            false,
            true
        ),
        GuideUi(
            "3", "Title 3", "", false, true
        ),
        GuideUi(
            "4", "Title 4", "", false, true
        ),
        GuideUi(
            "5", "Title 5", "", false, true
        ),
        GuideUi(
            "6", "Title 6", "", false, true
        )
    )
    InitialUi(drafts).Show({}, {}, {}) {}
}