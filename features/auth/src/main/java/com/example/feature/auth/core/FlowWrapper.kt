package com.example.feature.auth.core

import com.example.feature.auth.AuthUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

interface FlowWrapper {

    fun setValue(newState: AuthUiState)

    fun state(): StateFlow<AuthUiState>

    fun update(function: (AuthUiState) -> AuthUiState)

    class Base @Inject constructor() : FlowWrapper {

        private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Initial)

        override fun setValue(newState: AuthUiState) {
            _uiState.value = newState
        }

        override fun state(): StateFlow<AuthUiState> = _uiState.asStateFlow()

        override fun update(function: (AuthUiState) -> AuthUiState) {
            _uiState.update(function)
        }
    }
}