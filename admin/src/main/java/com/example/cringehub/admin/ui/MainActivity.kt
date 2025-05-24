package com.example.cringehub.admin.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.adminhome.navigation.AdminHomeRoute
import com.example.adminnavigation.AdminNavigation
import com.example.cringehub.theme.CringeHubTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel.Base by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init()
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
