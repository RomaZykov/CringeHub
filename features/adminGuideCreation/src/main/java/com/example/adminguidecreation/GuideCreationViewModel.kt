package com.example.adminGuideCreation

import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminGuideCreation.components.ContentItem
import com.example.adminGuideCreation.model.EditableGuideUi
import com.example.common.core.DispatcherList
import com.example.domain.repositories.admin.guide.GuideRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GuideCreationViewModel @Inject constructor(
    private val guideRepository: GuideRepository.Admin,
    private val dispatcherList: DispatcherList
) : ViewModel(), GuideCreationActions {
    private val uiStateMutable = MutableStateFlow<GuideCreationUiState>(EditableGuideUi())
    internal val uiState: StateFlow<GuideCreationUiState>
       get() = uiStateMutable.asStateFlow()

    override fun loadGuideWithId(guideId: String) {
        viewModelScope.launch(dispatcherList.io()) {
            guideRepository.fetchDraftGuides().collect { guides ->
                guides.find { desiredGuide -> desiredGuide.id == guideId }?.let { guide ->
                    uiStateMutable.value = EditableGuideUi(
                        guideId = guide.id,
                        title = guide.title,
                        // TODO: Add mapper
                        content = mutableMapOf<Int, List<ContentItem>>().apply {
                            guide.content.entries.forEach { (page, content) ->
                                val pageContent = mutableListOf<ContentItem>().apply {
                                    content.split('\n').forEach {
                                        if (guide.media.contains(it.toUri())) {
                                            add(ContentItem.Media.Image(content = it))
                                        } else {
                                            add(ContentItem.TextItem(content = it))
                                        }
                                    }
                                }
                                put(page, pageContent)
                            }
                        }
                    )
                }
            }
        }
    }

    override fun onPublishClicked() {
    }

    override fun saveContent(guide: EditableGuideUi) {
        viewModelScope.launch {
//            guideRepository.upsertGuide(id, title, content)
        }
    }
}

internal interface GuideCreationActions {

    fun onPublishClicked()

    fun loadGuideWithId(guideId: String)

    fun saveContent(guide: EditableGuideUi)
}
