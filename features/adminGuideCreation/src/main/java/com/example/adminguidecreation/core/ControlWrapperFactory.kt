package com.example.adminguidecreation.core

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import com.example.cringehub.theme.CringeHubTheme

interface ControlWrapperFactory {
    @Composable
    fun CreateAll()

    class Base(
        private val actionButtons: List<UIComponent>
    ) : ControlWrapperFactory {
        @Composable
        override fun CreateAll() {
            actionButtons.forEach {
                it.Display()
            }
        }
    }
}

class ConcreteActionButton(
    private val contentDesc: String,
    private val enabled: Boolean,
    private val paintRes: Int,
    private val onClicked: (Boolean) -> Unit,
) : UIComponent {
    @Composable
    override fun Display() {
        var currentSelected by rememberSaveable { mutableStateOf(false) }
        ControlWrapper(
            contentDesc = contentDesc,
            enabled = enabled,
            selected = currentSelected,
            onClick = {
                currentSelected = it
                onClicked.invoke(it)
            }
        ) {
            Icon(
                painter = painterResource(paintRes),
                contentDescription = null,
                tint = if (currentSelected) CringeHubTheme.colorScheme.secondary else CringeHubTheme.colorScheme.primary
            )
        }
    }
}

class ConcreteMediaActionButton(
    private val contentDesc: String,
    private val enabled: Boolean,
    private val paintRes: Int,
    private val onMediaClicked: (Uri) -> Unit
) : UIComponent {
    @Composable
    override fun Display() {
        val pickMedia =
            rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: $uri")
                    onMediaClicked.invoke(uri)
                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }
        ControlWrapper(
            contentDesc = contentDesc,
            enabled = enabled,
            selected = false,
            onClick = {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
            }
        ) {
            Icon(
                painter = painterResource(paintRes),
                contentDescription = null,
                tint = CringeHubTheme.colorScheme.secondary
            )
        }
    }
}

interface UIComponent {
    @Composable
    fun Display()
}