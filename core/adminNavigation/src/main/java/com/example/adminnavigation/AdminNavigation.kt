package com.example.adminnavigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class NavViewModel @Inject constructor(
    private val routeBuilders: Set<@JvmSuppressWildcards RouteBuilder>,
) : ViewModel() {
    fun buildRoutes(navGraphBuilder: NavGraphBuilder, navController: NavHostController) {
        routeBuilders.forEach {
            it.build(navGraphBuilder = navGraphBuilder, navController = navController)
        }
    }
}

@Composable
fun AdminNavigation(navController: NavHostController, startDestination: Any) {
    val viewModel = hiltViewModel<NavViewModel>()
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
