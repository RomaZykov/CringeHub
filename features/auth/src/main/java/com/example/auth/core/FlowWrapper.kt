package com.example.auth.core

import com.example.auth.AuthUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

interface FlowWrapper {

    fun setValue(newState: AuthUiState)

    fun state(): StateFlow<AuthUiState>

    fun value(): AuthUiState

    class Base @Inject constructor() : FlowWrapper {

        private val _uiState = MutableStateFlow(AuthUiState)

        override fun value(): AuthUiState = _uiState.value

        override fun setValue(newState: AuthUiState) {
            _uiState.value = newState
        }

        override fun state(): StateFlow<AuthUiState> = _uiState.asStateFlow()
    }
}