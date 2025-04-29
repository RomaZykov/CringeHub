package com.example.adminnavigation

import androidx.lifecycle.ViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val routeBuilders: Set<@JvmSuppressWildcards RouteBuilder>,
) : ViewModel() {

    fun buildRoutes(navGraphBuilder: NavGraphBuilder, navController: NavHostController) {
        routeBuilders.forEach {
            it.build(navGraphBuilder = navGraphBuilder, navController = navController)
        }
    }
}