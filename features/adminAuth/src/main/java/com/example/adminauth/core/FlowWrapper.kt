package com.example.adminauth.core

import com.example.adminauth.AdminAuthUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

interface FlowWrapper {
    fun setValue(newState: AdminAuthUiState)

    fun state(): StateFlow<AdminAuthUiState>

    fun update(function: (AdminAuthUiState) -> AdminAuthUiState)

    class Base @Inject constructor() : FlowWrapper {
        private val _uiState = MutableStateFlow<AdminAuthUiState>(AdminAuthUiState.Initial)
        override fun setValue(newState: AdminAuthUiState) {
            _uiState.value = newState
        }

        override fun state(): StateFlow<AdminAuthUiState> {
            return _uiState
        }

        override fun update(function: (AdminAuthUiState) -> AdminAuthUiState) {
            TODO("Not yet implemented")
        }
    }
}