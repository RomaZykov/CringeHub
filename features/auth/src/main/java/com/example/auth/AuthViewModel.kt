package com.example.auth

import androidx.lifecycle.ViewModel
import com.example.auth.core.FlowWrapper
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val flowWrapper: FlowWrapper = FlowWrapper.Base()
) : ViewModel() {

    fun state() = flowWrapper.state()

    fun update(newState: AuthUiState) = flowWrapper.setValue(newState)
}