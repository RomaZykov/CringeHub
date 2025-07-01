package com.example.adminguidecreation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.adminguidecreation.model.CurrentPreset

interface ContentItem {

    val content: String

    @Composable
    fun Display(
        currentPreset: CurrentPreset
    )

    data class TextItem(
        override val content: String = "",
        val selectionStart: Int = content.length,
        val selectionEnd: Int = content.length
    ) : ContentItem {
        @Composable
        override fun Display(
            currentPreset: CurrentPreset
        ) {
            val textFieldValue = TextFieldValue(
                content,
                TextRange(selectionStart, selectionEnd)
            )
            val currentTextEmpty = textFieldValue.text.isEmpty()
            val currentIndex = currentPreset.paragraphIndex

            val previousItemIsTextItem =
                currentIndex != 0 && currentPreset.contentState[currentIndex - 1] is TextItem
            val previousItemHasTextItem =
                previousItemIsTextItem && (currentPreset.contentState[currentIndex - 1] as TextItem).content.isNotEmpty()

            val nextItemIsTextItem =
                currentIndex + 1 < currentPreset.contentState.size && currentPreset.contentState[currentIndex + 1] is TextItem
            val nextItemHasTextItem =
                nextItemIsTextItem && (currentPreset.contentState[currentIndex + 1] as TextItem).content.isNotEmpty()

            TextField(
                modifier = Modifier
                    .semantics {
                        contentDescription = "${currentPreset.paragraphIndex} $TYPE_TEXT"
                    }
                    .fillMaxWidth()
                    .onKeyEvent {
                        when (it.key) {
                            Key.Backspace -> {
                                return@onKeyEvent if (currentTextEmpty && currentIndex != 0) {
                                    with(currentPreset) {
                                        focusManager.moveFocus(FocusDirection.Up)
                                        contentState.remove(contentState[paragraphIndex])
                                    }
                                    true
                                } else if (!currentTextEmpty && currentIndex != 0 && textFieldValue.selection.start == 0 && previousItemHasTextItem) {
                                    with(currentPreset) {
                                        val previousItemAsTextItem = (contentState[paragraphIndex - 1] as TextItem)
                                        contentState[paragraphIndex - 1] = TextItem(
                                            content = previousItemAsTextItem.content + textFieldValue.text,
                                            selectionStart = previousItemAsTextItem.selectionStart,
                                            selectionEnd = previousItemAsTextItem.selectionStart
                                        )
                                        focusManager.moveFocus(FocusDirection.Up)
                                        contentState.remove(contentState[paragraphIndex])
                                    }
                                    true
                                } else if (currentPreset.paragraphIndex == 0 && currentPreset.contentState.size == 2 && textFieldValue.text.length == 1 && !nextItemHasTextItem) {
                                    currentPreset.contentState.remove(currentPreset.contentState[1])
                                    true
                                } else if (currentPreset.contentState.size > 2 && currentPreset.paragraphIndex == 0 && textFieldValue.text.length == 1) {
                                    if (textFieldValue.text.isEmpty()) {
                                        currentPreset.contentState.remove(currentPreset.contentState[0])
                                    }
                                    true
                                } else {
                                    false
                                }
                            }

                            else -> false
                        }
                    }
                    .onFocusChanged {
                        if (it.isFocused) {
                            currentPreset.cursorParagraph.value = currentIndex
                        }
                    },
                value = textFieldValue,
                onValueChange = {
                    with(currentPreset) {
                        if (!it.text.contains('\n')) {
                            contentState[currentIndex] = TextItem(
                                content = it.text,
                                selectionStart = it.selection.start,
                                selectionEnd = it.selection.end
                            )
                        }
                        if (paragraphIndex + 1 == contentState.size && it.text.isNotEmpty()) {
                            contentState.add(
                                paragraphIndex + 1,
                                TextItem("")
                            )
                        }
                    }
                },
                placeholder = {
                    if (currentIndex == 0 && currentTextEmpty) {
                        Text("Content")
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        with(currentPreset) {
                            if (currentIndex + 1 < contentState.size && nextItemHasTextItem
                                && !currentTextEmpty
                            ) {
                                contentState.add(
                                    paragraphIndex + 1,
                                    TextItem("")
                                )
                            }
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    }
                )
            )
        }
    }

    interface Media : ContentItem {
        data class Image(override val content: String) : Media {
            @OptIn(ExperimentalGlideComposeApi::class)
            @Composable
            override fun Display(
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

        data class Gif(override val content: String) : Media {
            @Composable
            override fun Display(
                currentPreset: CurrentPreset
            ) {
                TODO("Not yet implemented")
            }
        }

        data class Video(override val content: String) : Media {
            @Composable
            override fun Display(
                currentPreset: CurrentPreset
            ) {
                TODO("Not yet implemented")
            }
        }
    }

    companion object {
        const val TYPE_TEXT = "text"
        const val TYPE_IMAGE = "image"
        const val TYPE_GIF = "gif"
        const val TYPE_VIDEO = "video"
    }
}

@Preview(showSystemUi = true)
@Composable
fun ContentItemImagePreview() {
    val cursorPage = remember { mutableIntStateOf(0) }
    val cursorParagraph = remember { mutableIntStateOf(0) }
    val currentPreset = CurrentPreset(
        contentState = SnapshotStateList(),
        currentPage = cursorPage,
        cursorParagraph = cursorParagraph,
        paragraphIndex = 0,
        focusManager = LocalFocusManager.current
    )
    ContentItem.Media.Image("").Display(currentPreset)
}