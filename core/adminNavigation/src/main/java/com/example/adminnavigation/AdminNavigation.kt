package com.example.adminnavigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun AdminNavigation(
    navController: NavHostController,
    startDestination: Any,
    viewModel: NavigationViewModel = hiltViewModel<NavigationViewModel>()
) {
    NavHost(navController = navController, startDestination = startDestination) {
        viewModel.buildRoutes(this, navController)
    }
}

fun NavController.isResumed(): Boolean {
    return currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED
}

fun NavController.navigateIfResumed(route: Any) {
    if (isResumed()) {
        navigate(route)
    }
}
