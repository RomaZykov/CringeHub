package com.example.adminguidecreation.core

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
        private val actionButtons: List<ConcreteActionButton>
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

interface UIComponent {
    @Composable
    fun Display()
}