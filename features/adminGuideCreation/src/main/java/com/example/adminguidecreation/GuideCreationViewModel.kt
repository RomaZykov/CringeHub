package com.example.adminguidecreation

import androidx.lifecycle.ViewModel
import com.example.common.core.FlowWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GuideCreationViewModel @Inject constructor(private val flowWrapper: FlowWrapper<GuideCreationUiState>) : ViewModel() {

    fun state() = flowWrapper.state()
}