package com.example.adminguidecreation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminguidecreation.model.GuideUi
import com.example.domain.repositories.admin.guide.GuideRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

interface GuideCreationViewModel {

    fun guideCreationUiStateFlow(): StateFlow<GuideCreationUiState>

    fun onPublishClicked()

    fun loadGuideWithId(guideId: String)

    fun saveContent(id: String, title: String, content: String)

    @HiltViewModel
    class Base @Inject constructor(
        private val guideRepository: GuideRepository.Admin
    ) : ViewModel(), GuideCreationViewModel {

        private val _uiState = MutableStateFlow<GuideCreationUiState>(GuideUi())

        override fun guideCreationUiStateFlow(): StateFlow<GuideCreationUiState> {
            return _uiState.asStateFlow()
        }

        override fun loadGuideWithId(guideId: String) {
            viewModelScope.launch(Dispatchers.IO) {
                guideRepository.fetchDraftGuides().collect { guides ->
                    guides.find { desiredGuide -> desiredGuide.id == guideId }?.let { guide ->
                        _uiState.update {
                            GuideUi(
                                guideId = guide.id,
                                title = guide.title,
                                content = guide.content
                            )
                        }
                    }
                }
            }
        }

        override fun onPublishClicked() {
            viewModelScope.launch {

            }
        }

        override fun saveContent(id: String, title: String, content: String) {
            viewModelScope.launch {
                guideRepository.upsertGuide(id, title, content)
            }
        }
    }
}