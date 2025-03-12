package com.example.adminguidecreation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cringehub.theme.CringeHubTheme
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor

interface GuideCreationUiState {

    @Composable
    fun Show(
        titleState: RichTextState,
        contentState: RichTextState,
        saveContent: () -> Unit,
        onPublishClicked: () -> Unit
    )

    object Initial : GuideCreationUiState {
        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        override fun Show(
            titleState: RichTextState,
            contentState: RichTextState,
            saveContent: () -> Unit,
            onPublishClicked: () -> Unit
        ) {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            Text("Создать гайд")
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                // Dialog to exit editor (then saving to draft) or continue working on it if there is a title or content
                            }) {
                                Icon(
                                    rememberVectorPainter(image = Icons.AutoMirrored.Filled.ArrowBack),
                                    contentDescription = "back from guide creation",
                                    tint = { Color.Black }
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = {
                                // dialog to publish or cancel
                            }) {
                                Icon(
                                    rememberVectorPainter(image = Icons.AutoMirrored.Filled.ArrowForward),
                                    contentDescription = "publish",
                                    tint = { Color.Black }
                                )
                            }
                        }
                    )
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Title(titleState)
                    RichTextEditor(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.9f),
                        state = contentState,
                        placeholder = {
                            Text(color = Color.Gray, text = "Контент")
                        }
                    )
                    EditorControls(
                        modifier = Modifier.wrapContentHeight(),
                        contentState = contentState,
                        onBoldClick = {
                            contentState.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.Bold))
                        }
                    )
                }
            }
        }
    }

    data class SaveContentToDraft(val title: String, val content: String) : GuideCreationUiState {
        @Composable
        override fun Show(
            titleState: RichTextState,
            contentState: RichTextState,
            saveContent: () -> Unit,
            onPublishClicked: () -> Unit
        ) {
            TODO("Not yet implemented")
        }
    }

    data class PublishContent(val title: String, val content: String) : GuideCreationUiState {
        @Composable
        override fun Show(
            titleState: RichTextState,
            contentState: RichTextState,
            saveContent: () -> Unit,
            onPublishClicked: () -> Unit
        ) {
            TODO("Not yet implemented")
        }
    }
}

@Composable
fun Title(titleState: RichTextState) {
    titleState.addSpanStyle(SpanStyle(fontWeight = FontWeight.Bold))
    RichTextEditor(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        state = titleState,
        placeholder = {
            Text(color = Color.Gray, text = "Заголовок")
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EditorControls(
    modifier: Modifier = Modifier,
    contentState: RichTextState,
    onBoldClick: () -> Unit
) {
    var boldSelected by rememberSaveable { mutableStateOf(false) }
    FlowRow(modifier = modifier.padding(8.dp)) {
        ControlWrapper(
            selected = boldSelected,
            onChangeClick = { boldSelected = it },
            onClick = onBoldClick
        ) {
            Icon(
                painter = painterResource(R.drawable.placeholder_icon),
                contentDescription = "Bold control",
                tint = if (boldSelected) CringeHubTheme.colorScheme.secondary else CringeHubTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun ControlWrapper(
    selected: Boolean,
    selectedColor: Color = CringeHubTheme.colorScheme.primary,
    unSelectedColor: Color = CringeHubTheme.colorScheme.secondary,
    onChangeClick: (Boolean) -> Unit,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .clickable {
                onClick()
                onChangeClick(!selected)
            }
            .background(
                if (selected)
                    selectedColor
                else
                    unSelectedColor
            )
            .border(
                width = 1.dp,
                color = CringeHubTheme.colorScheme.secondary,
                shape = RoundedCornerShape(size = 6.dp)
            )
            .padding(8.dp)
    ) {
        content()
    }
}

@Preview(showSystemUi = true)
@Composable
internal fun GuideCreationScreenPreview() {
    GuideCreationUiState.Initial.Show(rememberRichTextState(), rememberRichTextState(), {}) {}
}
