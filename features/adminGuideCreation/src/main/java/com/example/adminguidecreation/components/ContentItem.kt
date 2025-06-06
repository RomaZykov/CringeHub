package com.example.adminguidecreation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.net.toUri
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.adminguidecreation.model.CurrentPreset

interface ContentItem {

    @Composable
    fun Display(
        currentPreset: CurrentPreset
//        contentState: SnapshotStateList<ContentItem>,
//        cursorParagraph: MutableState<Int>,
//        paragraphIndex: Int,
//        focusManager: FocusManager
    )

    val content: String

    data class Text(
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

            val previousItemIsText =
                currentIndex != 0 && currentPreset.contentState[currentIndex - 1] is Text
            val previousItemHasText =
                previousItemIsText && (currentPreset.contentState[currentIndex - 1] as Text).content.isNotEmpty()

            val nextItemIsText =
                currentIndex + 1 < currentPreset.contentState.size && currentPreset.contentState[currentIndex + 1] is Text
            val nextItemHasText =
                nextItemIsText && (currentPreset.contentState[currentIndex + 1] as Text).content.isNotEmpty()

            TextField(
                modifier = Modifier
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
                                } else if (!currentTextEmpty && currentIndex != 0 && textFieldValue.selection.start == 0 && previousItemHasText) {
                                    with(currentPreset) {
                                        val previousItemAsText =
                                            (contentState[paragraphIndex - 1] as Text)
                                        contentState[paragraphIndex - 1] = Text(
                                            content = previousItemAsText.content + textFieldValue.text,
                                            selectionStart = previousItemAsText.selectionStart,
                                            selectionEnd = previousItemAsText.selectionStart
                                        )
                                        focusManager.moveFocus(FocusDirection.Up)
                                        contentState.remove(contentState[paragraphIndex])
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
                    if (!it.text.contains('\n')) {
                        currentPreset.contentState[currentIndex] = Text(
                            content = it.text,
                            selectionStart = it.selection.start,
                            selectionEnd = it.selection.end
                        )
                    }
                },
                placeholder = {
                    if (currentIndex == 0 && currentTextEmpty) {
                        androidx.compose.material3.Text("Content")
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        with(currentPreset) {
                            if (currentIndex + 1 < contentState.size && !nextItemHasText) {
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                            if (currentIndex + 1 < contentState.size && nextItemHasText
                                && !currentTextEmpty
                            ) {
                                contentState[paragraphIndex + 1] = Text("")
                                focusManager.moveFocus(FocusDirection.Down)
                            }
                            if (paragraphIndex + 1 == contentState.size && !currentTextEmpty) {
                                contentState.add(
                                    paragraphIndex + 1,
                                    Text("")
                                )
                                focusManager.moveFocus(FocusDirection.Down)
                            }
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
                Column(Modifier.fillMaxWidth()) {
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
}