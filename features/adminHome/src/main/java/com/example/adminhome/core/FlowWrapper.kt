package com.example.adminhome.core

import com.example.adminhome.AdminHomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

interface FlowWrapper {
    fun setValue(newState: AdminHomeUiState)

    fun state(): StateFlow<AdminHomeUiState>

    fun update(function: (AdminHomeUiState) -> AdminHomeUiState)

    class Base @Inject constructor() : FlowWrapper {
        private val _uiState = MutableStateFlow<AdminHomeUiState>(AdminHomeUiState.Initial)
        override fun setValue(newState: AdminHomeUiState) {
            _uiState.value = newState
        }

        override fun state(): StateFlow<AdminHomeUiState> {
            return _uiState
        }

        override fun update(function: (AdminHomeUiState) -> AdminHomeUiState) {
            _uiState.update(function)
        }
    }
}