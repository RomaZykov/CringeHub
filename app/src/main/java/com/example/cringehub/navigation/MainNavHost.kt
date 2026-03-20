package com.example.cringehub.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.feature.auth.AuthRoute
import com.example.feature.auth.authScreen
import com.example.feature.onboarding.OnBoardingRoute
import com.example.feature.onboarding.navigateToOnBoarding
import com.example.feature.onboarding.onBoardingScreen
import com.example.hub.HubRoute
import com.example.hub.hubScreen
import com.example.hub.navigateToHub

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavHost(
    navController: NavHostController = rememberNavController()
) {
    val isScaffoldBarsVisible = remember { mutableStateOf(false) }
    navController.addOnDestinationChangedListener { _, destination, _ ->
        isScaffoldBarsVisible.value =
            !(destination.hasRoute(AuthRoute::class) || destination.hasRoute(OnBoardingRoute::class))
    }
    Scaffold(
        topBar = {
            AnimatedVisibility(isScaffoldBarsVisible.value) {
                CenterAlignedTopAppBar({ Text("CringeHub") })
            }
        },
        bottomBar = {
            CringeHubBottomNavigation(navController, isScaffoldBarsVisible.value)
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            navController = navController,
            startDestination = AuthRoute
        ) {
            authScreen(navController::navigateToOnBoarding)
            onBoardingScreen(navController::navigateToHub)
            hubScreen()
        }
    }
}

@Composable
fun CringeHubBottomNavigation(
    navController: NavHostController,
    scaffoldState: Boolean
) {
    AnimatedVisibility(visible = scaffoldState) {
        NavigationBar {
            val topLevelRoutes = listOf(
                TopLevelRoute("Hub", HubRoute, ImageVector.vectorResource(Icons.Hub)),
                TopLevelRoute(
                    "Season pass",
                    HubRoute,
                    ImageVector.vectorResource(Icons.SeasonPass)
                ),
                TopLevelRoute("Store", HubRoute, ImageVector.vectorResource(Icons.Store)),
            )
            var selectedItem by remember { mutableIntStateOf(0) }
            topLevelRoutes.forEachIndexed { index, topLevelRoute ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            topLevelRoute.icon,
                            contentDescription = topLevelRoute.name,
                            tint = if (selectedItem == index) Color.Red else Color.Black
                        )
                    },
                    label = { Text(topLevelRoute.name) },
                    selected = selectedItem == index,
                    onClick = {
                        selectedItem = index
                        navController.navigate(topLevelRoute.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}
