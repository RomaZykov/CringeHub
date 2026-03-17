package com.example.adminGuideCreation.components

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.adminGuideCreation.R
import com.example.adminGuideCreation.model.CurrentPreset
import java.util.UUID

interface ContentItem {

    val uniqueKey: String
    val content: String

    @Composable
    fun Display(
        focusRequester: FocusRequester,
        currentPreset: CurrentPreset
    )

    data class TextItem(
        override val uniqueKey: String = UUID.randomUUID().toString(),
        override val content: String = "",
        val selectionStart: Int = content.length,
        val selectionEnd: Int = content.length
    ) : ContentItem {

        @Composable
        override fun Display(
            focusRequester: FocusRequester,
            currentPreset: CurrentPreset
        ) {
            val textFieldState = rememberTextFieldState(content)
            val currentPage = currentPreset.currentPage
            val currentParagraphIndex = currentPreset.paragraphIndex
            val needDownTransition = rememberSaveable { mutableStateOf(false) }

            val contentDesc = stringResource(
                R.string.guide_page_paragraph_content_desc,
                currentPage,
                currentParagraphIndex,
                TYPE_TEXT
            )

            if (currentPreset.isPressedDialog) {
                currentPreset.updateParagraphs.invoke(
                    currentPage, currentParagraphIndex, this@TextItem.copy(
                        content = textFieldState.text.toString(),
                        selectionStart = textFieldState.selection.start,
                        selectionEnd = textFieldState.selection.end
                    ), false
                )
            }

            TextField(
                modifier = Modifier
                    .semantics {
                        contentDescription = contentDesc
                    }
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .focusProperties {
                        canFocus = false
                        onExit = {
                            currentPreset.updateParagraphs.invoke(
                                currentPage, currentParagraphIndex, this@TextItem.copy(
                                    content = textFieldState.text.toString(),
                                    selectionStart = textFieldState.selection.start,
                                    selectionEnd = textFieldState.selection.end
                                ), needDownTransition.value
                            )
                            needDownTransition.value = false
                        }
                    }
                    .onPreviewKeyEvent {
                        when {
                            it.type == KeyEventType.KeyDown && it.key == Key.Backspace -> {
                                currentPreset.handleBackSpaceTransition.invoke(
                                    currentPage,
                                    currentParagraphIndex,
                                    this.copy(
                                        content = textFieldState.text.toString(),
                                        selectionStart = textFieldState.selection.start,
                                        selectionEnd = textFieldState.selection.end
                                    )
                                )
                                true
                            }

                            else -> false
                        }
                    }
                    .focusable(),
                state = textFieldState,
                placeholder = {
                    if (currentParagraphIndex == 0 && textFieldState.text.isEmpty()) {
                        Text("Content")
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                onKeyboardAction = {
                    needDownTransition.value = true
                    it.invoke()
                }
            )
        }
    }

    interface Media : ContentItem {
        data class Image(
            override val uniqueKey: String = UUID.randomUUID().toString(),
            override val content: String
        ) : Media {
            @OptIn(ExperimentalGlideComposeApi::class)
            @Composable
            override fun Display(
                focusRequester: FocusRequester,
                currentPreset: CurrentPreset
            ) {
                Column(
                    Modifier
                        .semantics {
                            contentDescription = "${currentPreset.paragraphIndex} $TYPE_IMAGE"
                        }
                        .fillMaxWidth()
                ) {
                    GlideImage(
                        modifier = Modifier,
                        model = content.toUri(),
                        contentDescription = null
                    )
                    Text("Additional info")
                }
            }
        }

        data class Gif(
            override val uniqueKey: String = UUID.randomUUID().toString(),
            override val content: String
        ) : Media {
            @Composable
            override fun Display(
                focusRequester: FocusRequester,
                currentPreset: CurrentPreset
            ) {
                TODO("Not yet implemented")
            }
        }

        data class Video(
            override val uniqueKey: String = UUID.randomUUID().toString(),
            override val content: String
        ) : Media {
            @Composable
            override fun Display(
                focusRequester: FocusRequester,
                currentPreset: CurrentPreset
            ) {
                TODO("Not yet implemented")
            }
        }
    }

    companion object {
        const val TYPE_TEXT = "text"
        const val TYPE_IMAGE = "image"
        const val TYPE_VIDEO = "video"
        const val TYPE_GIF = "gif"
    }
}

@Preview(showSystemUi = true)
@Composable
fun ContentItemImagePreview() {
    val cursorPage = remember { mutableIntStateOf(0) }
    val requester = remember { FocusRequester() }
    val currentPreset = CurrentPreset(
        { _, _, _ -> },
        { _, _, _, _ -> },
        false,
        currentPage = cursorPage.intValue,
        paragraphIndex = 0
    )
    ContentItem.Media.Image(content = "").Display(
        requester,
        currentPreset
    )
}