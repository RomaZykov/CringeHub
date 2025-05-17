package com.example.adminguidecreation.model

import androidx.compose.foundation.background
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
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adminguidecreation.GuideCreationUiState
import com.example.adminguidecreation.R
import com.example.adminguidecreation.core.ConcreteActionButton
import com.example.adminguidecreation.core.ControlWrapperFactory
import com.example.cringehub.theme.CringeHubTheme
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor

data class GuideUi(
    val guideId: String = "",
    val title: String = "",
    val content: String = ""
) : GuideCreationUiState {

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    override fun Show(
        popBackStack: () -> Unit,
        saveContent: (GuideUi) -> Unit,
        onPublishClicked: () -> Unit
    ) {
        val titleState = rememberRichTextState().apply {
            LaunchedEffect(key1 = title) {
                this@apply.setText(title)
            }
        }
        val contentState = rememberRichTextState().apply {
            LaunchedEffect(key1 = content) {
                this@apply.setText(content)
            }
        }

        val textFieldsEmpty =
            titleState.annotatedString.isBlank() && contentState.annotatedString.isBlank()

        var shouldOpenDialog by rememberSaveable { mutableStateOf(false) }
        if (shouldOpenDialog && !textFieldsEmpty) {
            saveContent.invoke(
                this.copy(
                    title = titleState.toText(),
                    content = contentState.toText()
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
        ) {
            Column(
                modifier = Modifier
                    .imePadding()
                    .fillMaxWidth()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var controlStateEnabled by remember { mutableStateOf(false) }
                val cursorParagraph by remember {
                    derivedStateOf {
                        getParagraphIndexPosition(contentState)
                    }
                }
                Title(titleState)
                Content(contentState) { focused ->
                    controlStateEnabled = focused
                }
                EditorControls(
                    modifier = Modifier
                        .semantics {
                            contentDescription = GuideCreationUiState.EDITOR_CONTROL
                        }
                        .wrapContentHeight(),
                    enabled = controlStateEnabled,
                    onBoldClicked = { selected ->
                        handleTextStyle(selected, contentState, cursorParagraph)
                    },
                    onQuoteClicked = {}
                )
            }
        }
    }
}

private fun handleTextStyle(
    selected: Boolean,
    contentState: RichTextState,
    cursorParagraph: Int
) {
    if (selected) {
        contentState.addSpanStyle(
            SpanStyle(fontWeight = FontWeight.Bold),
            TextRange(
                contentState.annotatedString.paragraphStyles[cursorParagraph].start,
                contentState.annotatedString.paragraphStyles[cursorParagraph].end
            )
        )
    } else {
        contentState.removeSpanStyle(
            SpanStyle(fontWeight = FontWeight.Bold),
            TextRange(
                contentState.annotatedString.paragraphStyles[cursorParagraph].start,
                contentState.annotatedString.paragraphStyles[cursorParagraph].end
            )
        )
    }
}

private fun getParagraphIndexPosition(contentState: RichTextState): Int {
    val cursor = contentState.selection.start
    val lineList = mutableListOf<Int>()
    contentState.toText().forEachIndexed { index, char ->
        if (char == '\n') {
            lineList.add(index)
        }
    }
    if (lineList.isEmpty()) {
        return 0
    }
    lineList.forEachIndexed { index, i ->
        if (cursor <= i) {
            return index
        }
    }
    return lineList.size
}

@Composable
private fun EditorControls(
    modifier: Modifier,
    enabled: Boolean,
    onBoldClicked: (Boolean) -> Unit,
    onQuoteClicked: (Boolean) -> Unit
) {
    val controlWrapperFactory: ControlWrapperFactory = ControlWrapperFactory.Base(
        listOf(
            ConcreteActionButton(
                contentDesc = GuideCreationUiState.BOLD_BUTTON,
                enabled = enabled,
                paintRes = R.drawable.placeholder_icon,
                onClicked = {
                    onBoldClicked.invoke(it)
                }
            ),
            ConcreteActionButton(
                contentDesc = GuideCreationUiState.QUOTE_BUTTON,
                enabled = enabled,
                paintRes = R.drawable.placeholder_icon,
                onClicked = {
                    onQuoteClicked.invoke(it)
                }
            ),
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
private fun Title(titleState: RichTextState) {
    RichTextEditor(
        modifier = Modifier
            .semantics {
                contentDescription = GuideCreationUiState.TITLE
            }
            .fillMaxWidth()
            .wrapContentHeight(),
        state = titleState,
        textStyle = TextStyle.Default.copy(
            fontSize = CringeHubTheme.typography.title.fontSize,
            fontWeight = CringeHubTheme.typography.title.fontWeight,
            lineHeight = TextUnit(CringeHubTheme.typography.title.fontSize.value, TextUnitType.Sp)
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
private fun ColumnScope.Content(contentState: RichTextState, focusChanged: (Boolean) -> Unit) {
    val fontPadding = 2
    RichTextEditor(
        state = contentState,
        modifier = Modifier
            .semantics {
                contentDescription = GuideCreationUiState.CONTENT
            }
            .onFocusChanged {
                focusChanged.invoke(it.isFocused)
            }
            .fillMaxWidth()
            .weight(0.9f),
        placeholder = {
            Text(fontSize = 18.sp, color = Color.Gray, text = stringResource(R.string.content))
        },
        textStyle = LocalTextStyle.current.merge(
            TextStyle(
                textAlign = TextAlign.Justify,
                fontFamily = CringeHubTheme.typography.body.fontFamily,
                fontSize = CringeHubTheme.typography.body.fontSize,
                letterSpacing = 0.2.sp,
                lineHeight = TextUnit(
                    CringeHubTheme.typography.body.fontSize.value + fontPadding,
                    TextUnitType.Sp
                ),
                baselineShift = BaselineShift(0.35f)
            )
        )
    )
}

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
        content = "long\n" +
                "longlong\n" +
                "longlonglonglonglonglonglonglonglonglong\n" +
                "longlonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglonglong"
    ).Show(
        {},
        { _ -> },
        {})
}
