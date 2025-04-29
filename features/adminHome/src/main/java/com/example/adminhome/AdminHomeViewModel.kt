package com.example.adminhome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.adminhome.model.GuideUi
import com.example.adminhome.model.InitialUi
import com.example.adminnavigation.DraftRouteProvider
import com.example.adminnavigation.GuideCreationRouteProvider
import com.example.adminnavigation.navigateIfResumed
import com.example.domain.repositories.admin.guide.GuideRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
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
        private val guideRepository: GuideRepository.Admin,
        private val ioDispatcher: CoroutineDispatcher
    ) : ViewModel(), AdminHomeViewModel {

        private val _uiState = MutableStateFlow<AdminHomeUiState>(
            InitialUi(
                emptyList()
            )
        )

        override fun adminHomeUiStateFlow(): StateFlow<AdminHomeUiState> = _uiState.asStateFlow()

        override fun init() {
            viewModelScope.launch(ioDispatcher) {
                combine(
                    guideRepository.fetchDraftGuides(),
                    guideRepository.fetchPublishedGuides()
                ) { draftGuides, publishedGuides ->
                    (draftGuides + publishedGuides).map { guide ->
                        GuideUi(
                            id = guide.id,
                            title = guide.title,
                            content = guide.content,
                            isFree = guide.isFree,
                            isDraft = guide.isDraft,
                        )
                    }
                }.collect {
                    _uiState.value = InitialUi(
                        allGuides = it
                    )
                }
            }
        }

        override fun navigateToDraft(navController: NavController, draftId: String) {
            navController.navigateIfResumed(draftRouteProvider.route(draftId))
        }

        override fun navigateToGuideCreation(navController: NavController) {
            navController.navigateIfResumed(guideCreationRouteProvider.route())
        }
    }
}
