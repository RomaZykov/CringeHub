package com.example.cringehub.ui

import android.graphics.Color.TRANSPARENT
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.cringehub.navigation.MainNavigation
import com.example.cringehub.theme.CringeHubTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(navigationBarStyle = lightTransparentStyle)
        super.onCreate(savedInstanceState)
        actionBar?.hide()

        setContent {
            CringeHubTheme {
                MainNavigation()
            }
        }
    }
}

private val lightTransparentStyle = SystemBarStyle.light(
    scrim = TRANSPARENT,
    darkScrim = TRANSPARENT
)