package com.example.adminguidecreation.model

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.adminguidecreation.GuideCreationUiState
import com.example.adminguidecreation.R
import com.example.adminguidecreation.components.ContentItem
import com.example.adminguidecreation.core.ConcreteActionButton
import com.example.adminguidecreation.core.ConcreteMediaActionButton
import com.example.adminguidecreation.core.ControlWrapperFactory
import com.example.cringehub.theme.CringeHubTheme

// GuideUi for admin could contain several pages with content inside (text, media)
data class GuideUi(
    val guideId: String = "",
    val title: String = "",
//    val content: Map<Int, List<ContentItem>> = mapOf(0 to listOf(ContentItem.TextItem("")))
    val content: List<ContentItem> = listOf(ContentItem.TextItem(""))
) : GuideCreationUiState {

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    override fun Show(
        popBackStack: () -> Unit,
        saveContent: (GuideUi) -> Unit,
        onPublishClicked: () -> Unit
    ) {
        val titleState = rememberSaveable(inputs = arrayOf(guideId)) { mutableStateOf(title) }
        val paragraphs = rememberSaveable(inputs = arrayOf(guideId), saver = ParagraphsListSaver) {
            mutableStateListOf<ContentItem>().apply {
                addAll(content)
            }
        }
        val textFieldsEmpty =
            titleState.value.isBlank() && paragraphs.toList().all { it.content.isEmpty() }

        var shouldOpenDialog by rememberSaveable { mutableStateOf(false) }
        if (shouldOpenDialog && !textFieldsEmpty) {
            saveContent.invoke(
                this.copy(
                    title = titleState.value,
                    content = paragraphs.toList()
                )
            )
            ShowDialog(onOpenDraftChanged = {
                shouldOpenDialog = it
            }, popBackStack)
        }

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(stringResource(R.string.create_guide_app_bar_title))
                    },
                    navigationIcon = {
                        IconButton(modifier = Modifier.semantics {
                            contentDescription = GuideCreationUiState.BACK_BUTTON
                        }, onClick = {
                            if (textFieldsEmpty) {
                                popBackStack.invoke()
                            } else {
                                shouldOpenDialog = true
                            }
                        }) {
                            Icon(
                                rememberVectorPainter(image = Icons.AutoMirrored.Filled.ArrowBack),
                                contentDescription = "back to home",
                                tint = { Color.Black }
                            )
                        }
                    },
                    actions = {
                        IconButton(modifier = Modifier.semantics {
                            contentDescription = GuideCreationUiState.PUBLISH_BUTTON
                        }, onClick = {
                            onPublishClicked.invoke()
                        }) {
                            Icon(
                                rememberVectorPainter(image = Icons.AutoMirrored.Filled.ArrowForward),
                                contentDescription = stringResource(R.string.publish),
                                tint = { Color.Black }
                            )
                        }
                    }
                )
            }
        ) { it ->
            Column(
                modifier = Modifier
                    .imePadding()
                    .fillMaxWidth()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Title(titleState)

                val contentCurrentPage = rememberSaveable { mutableIntStateOf(0) }
                val contentCurrentCursorParagraph = rememberSaveable { mutableIntStateOf(0) }
                var controlStateEnabled by remember { mutableStateOf(false) }
                val pages = rememberSaveable { mutableIntStateOf(1) }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.9f)
                ) {
                    items(pages.intValue) {
                        PageContent(
                            paragraphs,
                            contentCurrentPage,
                            contentCurrentCursorParagraph
                        ) { focused ->
                            controlStateEnabled = focused
                        }
                    }

                    // Create new page button
                    item {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(CringeHubTheme.dimensions.extraSmall),
                            onClick = { ++pages.intValue }) {
                            Text("Добавить страницу")
                        }
                    }
                }

                EditorControls(
                    modifier = Modifier
                        .semantics {
                            contentDescription = GuideCreationUiState.EDITOR_CONTROL
                        }
                        .wrapContentHeight(),
                    enabled = controlStateEnabled,
                    onBoldClicked = { selected ->
//                        handleTextStyle(selected, contentState, cursorParagraph)
                    },
                    onQuoteClicked = {},
                    onMediaClicked = { imageUri ->
                        imageUri.let {
                            paragraphs.add(
                                ++contentCurrentCursorParagraph.intValue,
                                ContentItem.Media.Image(it.toString())
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun EditorControls(
    modifier: Modifier,
    enabled: Boolean,
    onBoldClicked: (Boolean) -> Unit,
    onQuoteClicked: (Boolean) -> Unit,
    onMediaClicked: (Uri) -> Unit
) {
    val controlWrapperFactory: ControlWrapperFactory = ControlWrapperFactory.Base(
        listOf(
            ConcreteActionButton(
                contentDesc = GuideCreationUiState.BOLD_BUTTON,
                enabled = enabled,
                paintRes = com.example.ui.R.drawable.placeholder_small_icon,
                onClicked = {
                    onBoldClicked.invoke(it)
                }
            ),
            ConcreteActionButton(
                contentDesc = GuideCreationUiState.QUOTE_BUTTON,
                enabled = enabled,
                paintRes = com.example.ui.R.drawable.placeholder_small_icon,
                onClicked = {
                    onQuoteClicked.invoke(it)
                }
            ),
            ConcreteMediaActionButton(
                contentDesc = GuideCreationUiState.QUOTE_BUTTON,
                enabled = enabled,
                paintRes = com.example.ui.R.drawable.placeholder_small_icon,
                onMediaClicked = {
                    onMediaClicked.invoke(it)
                }
            )
        )
    )
    FlowRow(modifier = modifier.padding(8.dp)) {
        controlWrapperFactory.CreateAll()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShowDialog(
    onOpenDraftChanged: (Boolean) -> Unit,
    popBackStack: () -> Unit
) {
    BasicAlertDialog(modifier = Modifier.semantics {
        contentDescription = GuideCreationUiState.DIALOG
    }, onDismissRequest = {
        onOpenDraftChanged.invoke(false)
    }) {
        Box(
            modifier = Modifier
                .background(
                    CringeHubTheme.colorScheme.primary,
                    shape = RoundedCornerShape(CringeHubTheme.dimensions.medium)
                )
                .padding(CringeHubTheme.dimensions.medium),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Text(
                    modifier = Modifier.padding(vertical = CringeHubTheme.dimensions.small),
                    text = stringResource(R.string.draft_saved),
                    style = CringeHubTheme.typography.title
                )
                Row {
                    Button(
                        colors = ButtonDefaults.buttonColors()
                            .copy(containerColor = CringeHubTheme.colorScheme.errorContainer),
                        shape = RoundedCornerShape(CringeHubTheme.dimensions.medium),
                        onClick = {
                            popBackStack.invoke()
                        }) {
                        Text(text = stringResource(R.string.save))
                    }
                    Spacer(modifier = Modifier.padding(CringeHubTheme.dimensions.small))
                    Button(
                        colors = ButtonDefaults.buttonColors()
                            .copy(containerColor = CringeHubTheme.colorScheme.secondary),
                        shape = RoundedCornerShape(CringeHubTheme.dimensions.medium),
                        onClick = {
                            // Dismiss dialog
                            onOpenDraftChanged.invoke(false)
                        }
                    ) {
                        Text(text = stringResource(R.string.cancel))
                    }
                }
            }
        }
    }
}

@Composable
private fun Title(titleState: MutableState<String>) {
    TextField(
        modifier = Modifier
            .semantics {
                contentDescription = GuideCreationUiState.TITLE
            }
            .fillMaxWidth()
            .wrapContentHeight(),
        value = titleState.value,
        onValueChange = {
            titleState.value = it
        },
        textStyle = TextStyle.Default.copy(
            fontSize = CringeHubTheme.typography.title.fontSize,
            fontWeight = CringeHubTheme.typography.title.fontWeight
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Next,
        ),
        placeholder = {
            Text(color = Color.Gray, text = stringResource(R.string.title))
        }
    )
}

@Composable
private fun PageContent(
    contentState: SnapshotStateList<ContentItem>,
    currentPage: MutableState<Int>,
    cursorParagraph: MutableState<Int>,
    focusChanged: (Boolean) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val contentDescWithPageNumeration = stringResource(R.string.guide_page, currentPage.value)
    LazyColumn(
        modifier = Modifier
            .semantics {
                contentDescription = contentDescWithPageNumeration
            }
            .fillMaxWidth()
            .onFocusChanged {
                focusChanged.invoke(it.hasFocus)
            },
        userScrollEnabled = false
    ) {
        item {
            Text("${currentPage.value} Page")
        }
        contentState.forEachIndexed { paragraphIndex, textOrMedia ->
            item {
                textOrMedia.Display(
                    CurrentPreset(
                        contentState,
                        currentPage,
                        cursorParagraph,
                        paragraphIndex,
                        focusManager
                    )
                )
            }
        }
    }
}

data class CurrentPreset(
    val contentState: SnapshotStateList<ContentItem>,
    val currentPage: MutableState<Int>,
    val cursorParagraph: MutableState<Int>,
    val paragraphIndex: Int,
    val focusManager: FocusManager
)

private val ParagraphsListSaver = listSaver(
    save = { stateList ->
        // use types that is saveable to Bundle
        stateList.map {
            when (it) {
                is ContentItem.TextItem -> {
                    val type = ContentItem.TYPE_TEXT
                    val text = it.content
                    val start = it.selectionStart
                    val end = it.selectionEnd
                    listOf(type, text, start, end)
                }

                is ContentItem.Media.Image -> {
                    val type = ContentItem.TYPE_IMAGE
                    listOf(type, it.content)
                }

                is ContentItem.Media.Video -> {
                    val type = ContentItem.TYPE_VIDEO
                    listOf(type, it.content)
                }

                else -> IllegalArgumentException()
            }
        }
    },
    restore = { restoredList ->
        mutableStateListOf<ContentItem>().apply {
            if (restoredList.isNotEmpty()) {
                (restoredList as? List<List<*>>)?.forEach { itemData ->
                    val type = itemData[0] as? String ?: ""
                    when (type) {
                        ContentItem.TYPE_TEXT -> {
                            val text = itemData[1] as? String ?: ""
                            val start = itemData[2] as? Int ?: 0
                            val end = itemData[3] as? Int ?: 0
                            add(
                                ContentItem.TextItem(
                                    text,
                                    selectionStart = start,
                                    selectionEnd = end
                                )
                            )
                        }

                        ContentItem.TYPE_IMAGE -> {
                            val uri = itemData[1] as? String ?: ""
                            add(
                                ContentItem.Media.Image(
                                    uri
                                )
                            )
                        }

                        ContentItem.TYPE_VIDEO -> {
                            val uri = itemData[1] as? String ?: ""
                            add(
                                ContentItem.Media.Video(
                                    uri
                                )
                            )
                        }

                        else -> IllegalArgumentException()
                    }
                }
            }
        }
    }
)

@Preview
@Composable
internal fun DialogPreview() {
    ShowDialog(
        onOpenDraftChanged = { },
        popBackStack = { }
    )
}

@Preview(showSystemUi = true)
@Composable
internal fun GuideCreationScreenPreview() {
    GuideUi(
        content = ("long\n" +
                "longlong\n" +
                "longlonglonglonglonglonglonglonglonglong\n" +
                "longlonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglong").split(
            '\n'
        ).map { ContentItem.TextItem(it) }
    ).Show(
        {},
        { _ -> },
        {})
}
