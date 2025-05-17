package com.example.adminguidecreation.core

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.cringehub.theme.CringeHubTheme

@Composable
fun ControlWrapper(
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
            .padding(4.dp)
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
            .padding(12.dp)
    ) {
        content()
    }
}