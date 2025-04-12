package com.example.adminhome

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.adminhome.model.Initial
import com.example.adminnavigation.GuideCreationRouteProvider
import com.example.adminnavigation.navigateIfResumed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

interface AdminHomeViewModel {

    fun adminHomeUiStateFlow(): StateFlow<AdminHomeUiState>

    fun navigateToGuideCreation(navController: NavController) = Unit

    @HiltViewModel
    class Base @Inject constructor(
        private val guideCreationRouteProvider: GuideCreationRouteProvider
    ) : ViewModel(), AdminHomeViewModel {

        private val adminHomeScreenUiStateFlow = MutableStateFlow<AdminHomeUiState>(Initial)

        override fun adminHomeUiStateFlow(): StateFlow<AdminHomeUiState> {
            return adminHomeScreenUiStateFlow.asStateFlow()
        }

        override fun navigateToGuideCreation(navController: NavController) {
            navController.navigateIfResumed(guideCreationRouteProvider.route())
        }
    }
}
