package com.example.auth.core

import com.example.auth.AuthUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface FlowWrapper {
    fun value(): AuthUiState

    fun setValue(newState: AuthUiState)

    fun state(): StateFlow<AuthUiState>

    class Base : FlowWrapper {
        private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState())

        override fun value() = _uiState.value

        override fun setValue(newState: AuthUiState) {
            _uiState.value = newState
        }

        override fun state() = _uiState.asStateFlow()
    }
}