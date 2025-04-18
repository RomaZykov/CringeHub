package com.example.adminhome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.adminhome.components.DraftGuideItem
import com.example.adminhome.model.InitialUi
import com.example.adminnavigation.DraftRouteProvider
import com.example.adminnavigation.GuideCreationRouteProvider
import com.example.adminnavigation.navigateIfResumed
import com.example.domain.repositories.admin.guide.GuideRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface AdminHomeViewModel {

    fun adminHomeUiStateFlow(): StateFlow<AdminHomeUiState>

    fun init()

    fun navigateToGuideCreation(navController: NavController) = Unit

    fun navigateToDraft(navController: NavController, draftId: String) = Unit

    @HiltViewModel
    class Base @Inject constructor(
        private val guideCreationRouteProvider: GuideCreationRouteProvider,
        private val draftRouteProvider: DraftRouteProvider,
        private val guideRepository: GuideRepository.Admin
    ) : ViewModel(), AdminHomeViewModel {

        private val adminHomeScreenUiStateFlow = MutableStateFlow<AdminHomeUiState>(InitialUi(
            emptyList()
        ))

        override fun init() {
            viewModelScope.launch {
                guideRepository.fetchDraftGuides().collect {
                    adminHomeScreenUiStateFlow.value = InitialUi(
                        drafts = it.map { draftGuide ->
                            DraftGuideItem(
                                id = draftGuide.id,
                                title = draftGuide.title,
                                content = draftGuide.content
                            )
                        }
                    )
                }
            }
        }

        override fun adminHomeUiStateFlow(): StateFlow<AdminHomeUiState> {
            return adminHomeScreenUiStateFlow.asStateFlow()
        }

        override fun navigateToDraft(navController: NavController, draftId: String) {
            navController.navigateIfResumed(draftRouteProvider.route(draftId))
        }

        override fun navigateToGuideCreation(navController: NavController) {
            navController.navigateIfResumed(guideCreationRouteProvider.route())
        }
    }
}
