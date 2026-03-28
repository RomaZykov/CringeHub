package com.example.adminGuideCreation.model

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.adminGuideCreation.GuideCreationUiState
import com.example.adminGuideCreation.GuideCreationUserActions
import com.example.adminGuideCreation.R
import com.example.adminGuideCreation.components.ContentItem
import com.example.adminGuideCreation.components.ContentItem.TextItem
import com.example.adminGuideCreation.core.ConcreteActionButton
import com.example.adminGuideCreation.core.ControlWrapperFactory
import com.example.cringehub.theme.CringeHubTheme

/**
 * GuideUi for admin could contain several pages with content inside (text or some media)
 */
data class EditableGuideUi(
    private val guideId: String = "",
    private val title: String = "",
    private val content: Map<Int, List<ContentItem>> = mapOf(0 to listOf(TextItem("")))
) : GuideCreationUiState {

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    override fun Show(
        guideCreationUserActions: GuideCreationUserActions,
        onSaveContent: (EditableGuideUi) -> Unit,
    ) {
        val titleState = rememberSaveable(inputs = arrayOf(guideId)) { mutableStateOf(title) }
        val paragraphsWithPages =
            rememberSaveable(inputs = arrayOf(guideId), saver = ParagraphsWithPagesSaver) {
                mutableStateMapOf<Int, List<ContentItem>>().apply {
                    putAll(content)
                }
            }

        var shouldOpenDialog by rememberSaveable { mutableStateOf(false) }
        if (shouldOpenDialog) {
            ShowDialog(onOpenDraftChanged = {
                shouldOpenDialog = it
            }, { guideCreationUserActions.popBackStack() })
            onSaveContent.invoke(
                this.copy(
                    title = titleState.value,
                    content = paragraphsWithPages.toMap()
                )
            )
        }
        val backInteractionSource = remember { MutableInteractionSource() }
        val backPressed by backInteractionSource.collectIsPressedAsState()
        Scaffold(
            modifier = Modifier.semantics {
                contentDescription = GuideCreationUiState.GUIDE_CREATION_SCREEN
            },
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(stringResource(R.string.create_guide_app_bar_title))
                    },
                    navigationIcon = {
                        IconButton(modifier = Modifier.semantics {
                            contentDescription = GuideCreationUiState.BACK_BUTTON
                        }, onClick = {
                            val contentFieldsEmpty =
                                !paragraphsWithPages.any { it.value.any { item -> item.content.isNotBlank() } }
                            val textFieldsEmpty = titleState.value.isEmpty() && contentFieldsEmpty
                            if (textFieldsEmpty) {
                                guideCreationUserActions.popBackStack()
                            } else {
                                shouldOpenDialog = true
                            }
                        }, interactionSource = backInteractionSource) {
                            Icon(
                                rememberVectorPainter(image = ImageVector.vectorResource(com.example.ui.R.drawable.placeholder_small_icon)),
                                contentDescription = "back to home",
                                tint = { Color.Black }
                            )
                        }
                    },
                    actions = {
                        IconButton(modifier = Modifier.semantics {
                            contentDescription = GuideCreationUiState.PUBLISH_BUTTON
                        }, onClick = {
                            guideCreationUserActions.onPublishClicked()
                        }) {
                            Icon(
                                rememberVectorPainter(
                                    image = ImageVector.vectorResource(com.example.ui.R.drawable.placeholder_small_icon)
                                ),
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
                PageContent(
                    paragraphsWithPages,
                    backPressed,
                    contentCurrentPage,
                    contentCurrentCursorParagraph
                ) { focused ->
                    controlStateEnabled = focused
                }

                EditorControls(
                    modifier = Modifier
                        .wrapContentHeight(),
                    enabled = controlStateEnabled,
                    onBoldClicked = { selected ->
//                        handleTextStyle(selected, paragraphsWithPages, cursorParagraph)
                    },
                    onQuoteClicked = {},
                    onMediaClicked = { imageUri ->
                        imageUri.let {
                            ++contentCurrentCursorParagraph.intValue
                            paragraphsWithPages[contentCurrentPage.intValue]?.plus(
                                ContentItem.Media.Image(
                                    content = it.toString()
                                )
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
//            ConcreteMediaActionButton(
//                contentDesc = GuideCreationUiState.QUOTE_BUTTON,
//                enabled = enabled,
//                paintRes = com.example.ui.R.drawable.placeholder_small_icon,
//                onMediaClicked = {
//                    onMediaClicked.invoke(it)
//                }
//            )
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

private fun calculateGlobalIndex(
    pagesWithParagraphs: Map<Int, List<ContentItem>>,
    desiredPageIndex: Int,
    desiredParagraphIndex: Int
): Int {
    val prefixSum = pagesWithParagraphs.toList().runningFold(0) { acc, (_, content) ->
        acc + content.size
    }
    var actualIndexInMap = 0
    pagesWithParagraphs.toList().mapIndexed { index, (_, paragraphs) ->
        if (desiredPageIndex == index) {
            val offset = prefixSum[index]
            paragraphs.indices.map { paragraphsIndex ->
                if (desiredParagraphIndex == paragraphsIndex) {
                    actualIndexInMap = offset + desiredParagraphIndex
                }
            }
        }
    }
    return actualIndexInMap
}

@Composable
private fun ColumnScope.PageContent(
    paragraphsState: SnapshotStateMap<Int, List<ContentItem>>,
    isPressedDialog: Boolean,
    currentPage: MutableState<Int>,
    cursorParagraph: MutableState<Int>,
    focusChanged: (Boolean) -> Unit
) {
    val downTransition = rememberSaveable { mutableStateOf(false) }
    val backspaceTransition = rememberSaveable { mutableStateOf(false) }
    val requestFocusToNewPosition = rememberSaveable { mutableIntStateOf(0) }

    val listState = rememberLazyListState()
    val focusRequesters = remember(paragraphsState.toMap()) {
        val totalContentItemsSize = paragraphsState.values.sumOf { it.size }
        mutableStateListOf<FocusRequester>().apply {
            addAll(List(totalContentItemsSize) { FocusRequester() })
        }
    }

    fun handleComplexTextClearingTransition(
        pageIndex: Int,
        currentParagraphIndex: Int,
        contentToHandle: ContentItem
    ) {
        val currentContentStateInPage = paragraphsState.getValue(pageIndex)
        val currentTextEmpty = contentToHandle.content.isEmpty()

        val previousItemIsText =
            currentParagraphIndex != 0 && currentContentStateInPage[currentParagraphIndex - 1] is TextItem
        val previousItemHasTextItem =
            previousItemIsText && currentContentStateInPage[currentParagraphIndex - 1].content.isNotEmpty()

        val nextItemIsText =
            currentParagraphIndex + 1 < currentContentStateInPage.size && currentContentStateInPage[currentParagraphIndex + 1] is TextItem
        val nextItemHasText =
            nextItemIsText && currentContentStateInPage[currentParagraphIndex + 1].content.isNotEmpty()

        if (currentTextEmpty && currentParagraphIndex != 0) {
            val updatedContent = currentContentStateInPage.toMutableList()
            updatedContent.remove(currentContentStateInPage[currentParagraphIndex])
            paragraphsState[pageIndex] = updatedContent
        } else if (!currentTextEmpty && currentParagraphIndex != 0 && previousItemHasTextItem) {
            if (contentToHandle is TextItem && contentToHandle.selectionStart == 0) {
                val previousItemAsTextItem =
                    (currentContentStateInPage[currentParagraphIndex - 1] as TextItem)
                val updatedContent =
                    currentContentStateInPage.toMutableList()
                updatedContent[currentParagraphIndex - 1] = TextItem(
                    content = previousItemAsTextItem.content + contentToHandle.content,
                    selectionStart = previousItemAsTextItem.selectionStart,
                    selectionEnd = previousItemAsTextItem.selectionStart
                )
                updatedContent.remove(currentContentStateInPage[currentParagraphIndex])
                paragraphsState[pageIndex] = updatedContent
            }
        } else if (currentParagraphIndex == 0 && currentContentStateInPage.size == 2 && contentToHandle.content.length == 1 && !nextItemHasText) {
            val updatedContent = currentContentStateInPage.toMutableList()
            updatedContent.remove(currentContentStateInPage[1])
            paragraphsState[pageIndex] = updatedContent
        } else if (currentContentStateInPage.size > 2 && currentParagraphIndex == 0 && contentToHandle.content.length == 1) {
            if (currentTextEmpty) {
                val updatedContent =
                    currentContentStateInPage.toMutableList()
                updatedContent.remove(currentContentStateInPage[0])
                paragraphsState[pageIndex] = updatedContent
            }
        }
    }

    fun updateContentState(
        pageIndex: Int,
        currentParagraphIndex: Int,
        currentContent: ContentItem,
        needDownTransition: Boolean
    ) {
        val currentContentStateInPage = paragraphsState.getValue(pageIndex)
        val updatedContent = currentContentStateInPage.toMutableList()
        updatedContent[currentParagraphIndex] = currentContent
        if (needDownTransition) {
            val nextItemIsText = currentParagraphIndex + 1 < currentContentStateInPage.size
                    && currentContentStateInPage[currentParagraphIndex + 1] is TextItem
            val nextItemHasText = nextItemIsText
                    && currentContentStateInPage[currentParagraphIndex + 1].content.isNotEmpty()
            if (currentParagraphIndex == currentContentStateInPage.size - 1 || nextItemHasText) {
                if (nextItemHasText) {
                    updatedContent.add(currentParagraphIndex + 1, TextItem())
                } else {
                    updatedContent.add(TextItem())
                }
                paragraphsState[pageIndex] = updatedContent
            }
        } else {
            paragraphsState[pageIndex] = updatedContent
        }
    }

    LaunchedEffect(downTransition.value, backspaceTransition.value) {
        if (downTransition.value || backspaceTransition.value) {
            listState.requestScrollToItem(requestFocusToNewPosition.intValue)
            focusRequesters[requestFocusToNewPosition.intValue].requestFocus(FocusDirection.Next)
            downTransition.value = false
            backspaceTransition.value = false
            requestFocusToNewPosition.intValue = 0
        }
    }

    val contentDescWithPageNumeration = stringResource(R.string.guide_page, currentPage.value)
    LazyColumn(
        modifier = Modifier
            .semantics {
                contentDescription = contentDescWithPageNumeration
            }
            .fillMaxWidth()
            .focusGroup()
            .onFocusChanged {
                focusChanged.invoke(it.hasFocus)
            }
            .weight(0.9f),
        state = listState
    ) {
        paragraphsState.entries.forEach { (pageIndex, pageContent) ->
            item {
                Text("${pageIndex + 1} Page")
            }
            itemsIndexed(
                pageContent,
                key = { _, item -> item.uniqueKey })
            { paragraphIndex, content ->
//                currentPage.value = pageIndex
//                cursorParagraph.value = paragraphIndex
                val calculatedIndex =
                    calculateGlobalIndex(paragraphsState.toMap(), pageIndex, paragraphIndex)
                content.Display(
                    focusRequesters[calculatedIndex],
                    CurrentPreset(
                        handleBackSpaceTransition = { pageIndex, parIndex, contentItem ->
                            handleComplexTextClearingTransition(
                                pageIndex,
                                parIndex,
                                contentItem
                            )
                            backspaceTransition.value = true
                            requestFocusToNewPosition.intValue = calculatedIndex - 1
                        },
                        updateParagraphs = { pageIndex, parIndex, newContent, needDownTransition ->
                            updateContentState(
                                pageIndex,
                                parIndex,
                                newContent,
                                needDownTransition
                            )
                            downTransition.value = needDownTransition
                            requestFocusToNewPosition.intValue = calculatedIndex + 1
                        },
                        isPressedDialog,
                        currentPage = pageIndex,
                        paragraphIndex = paragraphIndex
                    )
                )
            }
        }
        item {
            CreateNewPageButton(paragraphsState)
        }
    }
}

@Composable
private fun CreateNewPageButton(paragraphsState: SnapshotStateMap<Int, List<ContentItem>>) {
    Button(
        modifier = Modifier
            .semantics {
                contentDescription = GuideCreationUiState.CREATE_PAGE_BUTTON
            }
            .fillMaxWidth()
            .padding(CringeHubTheme.dimensions.extraSmall),
        onClick = {
            paragraphsState[paragraphsState.size] = mutableListOf(TextItem(content = ""))
        }) {
        Text("Добавить страницу")
    }
}

data class CurrentPreset(
    val handleBackSpaceTransition: (pageIndex: Int, currentParagraphIndex: Int, contentToHandle: ContentItem) -> Unit,
    val updateParagraphs: (pageIndex: Int, currentParagraphIndex: Int, newContent: ContentItem, needDownTransition: Boolean) -> Unit,
    val isPressedDialog: Boolean,
//    val shouldRequestFocus: Boolean,
    val currentPage: Int,
    val paragraphIndex: Int,
)

private val ParagraphsWithPagesSaver = mapSaver(
    save = { stateMap ->
        mutableStateMapOf<String, List<Any>>().apply {
            stateMap.forEach { (page, paragraphs) ->
                val bundledParagraphs = paragraphs.map {
                    val uniqueId = it.uniqueKey
                    when (it) {
                        is TextItem -> {
                            val type = ContentItem.TYPE_TEXT
                            val text = it.content
                            val start = it.selectionStart
                            val end = it.selectionEnd
                            listOf(uniqueId, type, text, start, end)
                        }

                        is ContentItem.Media.Image -> {
                            val type = ContentItem.TYPE_IMAGE
                            listOf(uniqueId, type, it.content)
                        }

                        is ContentItem.Media.Video -> {
                            val type = ContentItem.TYPE_VIDEO
                            listOf(uniqueId, type, it.content)
                        }

                        else -> IllegalArgumentException()
                    }
                }
                put(page.toString(), bundledParagraphs.toMutableList())
            }
        }
    },
    restore = { restoredMap ->
        mutableStateMapOf<Int, List<ContentItem>>().apply {
            if (restoredMap.isNotEmpty()) {
                (restoredMap as? Map<*, *>)?.entries?.forEach { entry ->
                    val page = (entry.key as? String ?: "").toInt()
                    val paragraphs = mutableListOf<ContentItem>().apply {
                        (entry.value as? List<*>)?.forEach {
                            if (it is List<*>) {
                                val uniqueId = it[0] as? String ?: ""
                                val type = it[1] as? String ?: ""
                                when (type) {
                                    ContentItem.TYPE_TEXT -> {
                                        val text = it[2] as? String ?: ""
                                        val start = it[3] as? Int ?: 0
                                        val end = it[4] as? Int ?: 0
                                        add(
                                            TextItem(
                                                uniqueId,
                                                text,
                                                selectionStart = start,
                                                selectionEnd = end
                                            )
                                        )
                                    }

                                    ContentItem.TYPE_IMAGE -> {
                                        val uri = it[2] as? String ?: ""
                                        add(
                                            ContentItem.Media.Image(
                                                uniqueId,
                                                content = uri
                                            )
                                        )
                                    }

                                    ContentItem.TYPE_VIDEO -> {
                                        val uri = it[2] as? String ?: ""
                                        add(
                                            ContentItem.Media.Video(
                                                uniqueId,
                                                content = uri
                                            )
                                        )
                                    }

                                    else -> IllegalArgumentException()
                                }
                            }
                        }
                    }
                    put(page, paragraphs)
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
    EditableGuideUi(
        content = mapOf(
            0 to ("long\n" +
                    "longlong\n" +
                    "longlonglonglonglonglonglonglonglonglong\n" +
                    "longlonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglong").split(
                '\n'
            ).map { TextItem(it) }
        )
    ).Show(
        GuideCreationUserActions.ForPreview
    ) { _ -> }
}
