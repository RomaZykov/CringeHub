package com.example.adminguidecreation

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminguidecreation.components.ContentItem
import com.example.adminguidecreation.model.GuideUi
import com.example.domain.repositories.admin.guide.GuideRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface GuideCreationViewModel {

    fun guideCreationUiStateFlow(): StateFlow<GuideCreationUiState>

    fun onPublishClicked()

    fun loadGuideWithId(guideId: String)

    fun saveContent(id: String, title: String, content: String, media: List<Uri>)

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
                        _uiState.value = GuideUi(
                            guideId = guide.id,
                            title = guide.title,
                            content = mutableListOf<ContentItem>().apply {
                                guide.content.split('\n').forEach {
                                    // TODO: Option only with Images
                                    if (guide.media.contains(it.toUri())) {
                                        add(ContentItem.Media.Image(it))
                                    } else {
                                        add(ContentItem.Text(it))
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }

        override fun onPublishClicked() {
            viewModelScope.launch {

            }
        }

        override fun saveContent(id: String, title: String, content: String, media: List<Uri>) {
            viewModelScope.launch {
                guideRepository.upsertGuide(id, title, content)
            }
        }
    }
}