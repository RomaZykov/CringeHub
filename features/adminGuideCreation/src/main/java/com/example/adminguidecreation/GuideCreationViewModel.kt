package com.example.adminguidecreation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.core.FlowWrapper
import com.example.domain.repositories.admin.guide.GuideRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GuideCreationViewModel @Inject constructor(
    private val flowWrapper: FlowWrapper<GuideCreationUiState>,
    private val guideRepository: GuideRepository.Admin
) : ViewModel() {

    fun state() = flowWrapper.state()

    fun onPublishClicked() {
        viewModelScope.launch {

        }
    }

    fun saveContent() {
        viewModelScope.launch {

        }
    }
}