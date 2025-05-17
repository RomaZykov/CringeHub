package com.example.cringehub.admin.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.adminhome.navigation.AdminHomeRoute
import com.example.adminnavigation.AdminNavigation
import com.example.cringehub.theme.CringeHubTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CringeHubTheme {
                AdminNavigation(
                    navController = rememberNavController(),
                    // TODO: Replace for prod
                    startDestination = AdminHomeRoute
                )
            }
        }
    }
}
