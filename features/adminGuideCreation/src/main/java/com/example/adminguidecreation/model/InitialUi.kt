package com.example.adminguidecreation.model

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.adminguidecreation.GuideCreationUiState
import com.example.adminguidecreation.R
import com.example.cringehub.theme.CringeHubTheme
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor

object InitialUi : GuideCreationUiState {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Show(
        popBackStack: () -> Unit,
        saveContent: (String, String) -> Unit,
        onPublishClicked: () -> Unit
    ) {
        val titleState = rememberRichTextState()
        val contentState = rememberRichTextState()
        val whenTextFieldsEmpty =
            titleState.annotatedString.isBlank() && contentState.annotatedString.isBlank()
        var openDialog by rememberSaveable { mutableStateOf(false) }
        if (openDialog && !whenTextFieldsEmpty) {
            ShowDialog(onOpenDraftChanged = {
                openDialog = it
            }, popBackStack, saveContent, titleState, contentState)
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
                            if (whenTextFieldsEmpty) {
                                popBackStack.invoke()
                            } else {
                                openDialog = true
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
                val cursorParagraph by remember {
                    derivedStateOf {
                        getParagraphIndexPosition(contentState)
                    }
                }
                var controlStateEnabled by remember { mutableStateOf(false) }
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
                    onBoldClick = { selected ->
                        handleTextStyle(selected, contentState, cursorParagraph)
                    }
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun EditorControls(
    modifier: Modifier,
    enabled: Boolean,
    onBoldClick: (Boolean) -> Unit,
) {
    var boldSelected by rememberSaveable { mutableStateOf(false) }
    FlowRow(modifier = modifier.padding(8.dp)) {
        // Make paragraph in bold style
        ControlWrapper(
            contentDesc = GuideCreationUiState.BOLD_BUTTON,
            enabled = enabled,
            selected = boldSelected,
            onClick = { selected ->
                boldSelected = selected
                onBoldClick.invoke(boldSelected)
            }
        ) {
            Icon(
                painter = painterResource(R.drawable.placeholder_icon),
                contentDescription = "bold control",
                tint = if (boldSelected) CringeHubTheme.colorScheme.secondary else CringeHubTheme.colorScheme.primary
            )
        }
        // Add quote
        // Add image
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShowDialog(
    onOpenDraftChanged: (Boolean) -> Unit,
    popBackStack: () -> Unit,
    saveContent: (String, String) -> Unit,
    titleState: RichTextState,
    contentState: RichTextState
) {
    saveContent.invoke(titleState.toText(), contentState.toText())
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
    RichTextEditor(
        modifier = Modifier
            .semantics {
                contentDescription = GuideCreationUiState.CONTENT
            }
            .onFocusChanged {
                focusChanged.invoke(it.isFocused)
            }
            .fillMaxWidth()
            .weight(0.9f),
        state = contentState,
        placeholder = {
            Text(color = Color.Gray, text = stringResource(R.string.content))
        },
        textStyle = TextStyle.Default.copy(
            textAlign = TextAlign.Justify,
            fontSize = CringeHubTheme.typography.body.fontSize,
            baselineShift = BaselineShift(0.75f),
            lineHeight = TextUnit(value = 24f, type = TextUnitType.Sp),
            lineBreak = LineBreak.Paragraph.copy(
                strategy = LineBreak.Strategy.HighQuality,
                strictness = LineBreak.Strictness.Normal,
                wordBreak = LineBreak.WordBreak.Phrase
            )
        )
    )
}

@Composable
private fun ControlWrapper(
    selected: Boolean,
    enabled: Boolean,
    onClick: (Boolean) -> Unit,
    contentDesc: String,
    content: @Composable () -> Unit
) {
    val selectedColor: Color = CringeHubTheme.colorScheme.primary
    val unSelectedColor: Color = CringeHubTheme.colorScheme.secondary
    val notEnabledColor: Color = CringeHubTheme.colorScheme.error
    Box(
        modifier = Modifier
            .semantics {
                contentDescription = contentDesc
            }
            .clip(RoundedCornerShape(6.dp))
            .clickable(enabled = enabled) {
                onClick.invoke(!selected)
            }
            .background(
                if (enabled) {
                    if (selected)
                        selectedColor
                    else
                        unSelectedColor
                } else {
                    notEnabledColor
                }
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

@Preview
@Composable
internal fun DialogPreview() {
    ShowDialog(
        onOpenDraftChanged = { },
        popBackStack = { },
        saveContent = { _, _ -> },
        titleState = rememberRichTextState(),
        contentState = rememberRichTextState()
    )
}

@Preview(showSystemUi = true)
@Composable
internal fun GuideCreationScreenPreview() {
    InitialUi.Show(
        {},
        { _, _ -> },
        {})
}
