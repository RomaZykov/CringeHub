package com.example.adminguidecreation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminguidecreation.model.InitialUi
import com.example.domain.repositories.admin.guide.GuideRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface GuideCreationViewModel {

    fun guideCreationUiStateFlow(): StateFlow<GuideCreationUiState>

    fun onPublishClicked()

    fun saveContent(title: String, content: String)

    @HiltViewModel
    class Base @Inject constructor(
        private val guideRepository: GuideRepository.Admin
    ) : ViewModel(), GuideCreationViewModel {

        private val _uiState = MutableStateFlow<GuideCreationUiState>(InitialUi)

        override fun guideCreationUiStateFlow(): StateFlow<GuideCreationUiState> {
            return _uiState.asStateFlow()
        }

        override fun onPublishClicked() {
            viewModelScope.launch {

            }
        }

        override fun saveContent(title: String, content: String) {
            viewModelScope.launch {
//                guideRepository.saveGuideAsDraft(title, content)
            }
        }
    }
}