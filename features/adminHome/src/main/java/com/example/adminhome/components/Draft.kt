package com.example.adminhome.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.adminhome.R
import com.example.adminhome.model.GuideUi
import com.example.cringehub.theme.CringeHubTheme

@Composable
fun Draft(guide: GuideUi, onDraftClicked: (String) -> Unit, onDraftDeleted: (String) -> Unit) {
    val contentDesc = stringResource(R.string.draft, guide.title)
    Card(
        modifier = Modifier
            .semantics {
                contentDescription = contentDesc
            }
            .clickable {
                onDraftClicked.invoke(guide.id)
            }
            .fillMaxWidth()
            .padding(
                vertical = CringeHubTheme.dimensions.small
            )
    ) {
        val modifier = Modifier.padding(
            top = CringeHubTheme.dimensions.small,
            start = CringeHubTheme.dimensions.small,
            end = CringeHubTheme.dimensions.small
        )
        Row {
            Column(modifier = Modifier.fillMaxWidth(0.8f)) {
                Text(
                    modifier = modifier,
                    text = guide.title,
                    style = CringeHubTheme.typography.body,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = modifier.padding(bottom = CringeHubTheme.dimensions.small),
                    text = guide.content,
                    style = CringeHubTheme.typography.body,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(CringeHubTheme.dimensions.small))
                    .align(Alignment.CenterVertically)
                    .background(
                        Color.Red
                    )
                    .padding(CringeHubTheme.dimensions.extraSmall)
                    .clickable {
                        onDraftDeleted.invoke(guide.id)
                    }
            ) {
                Icon(
                    painter = painterResource(com.example.ui.R.drawable.placeholder_small_icon),
                    contentDescription = null,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DraftPreview() {
    Draft(
        GuideUi(
            "0",
            "Preview title",
            "Preview content",
            false,
            true
        ), {}) { }
}