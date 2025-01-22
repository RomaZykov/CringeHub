package com.example.adminhome.core

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

//interface FlowWrapper {
//    fun setValue(newState: AdminHomeUiState)
//
//    fun state(): StateFlow<AdminHomeUiState>
//
//    fun update(function: (AdminHomeUiState) -> AdminHomeUiState)
//
//    class Base : FlowWrapper {
//        private val _uiState = MutableStateFlow<AdminHomeUiState>(AdminHomeUiState.Initial)
//        override fun setValue(newState: AdminHomeUiState) {
//            _uiState.value = newState
//        }
//
//        override fun state(): StateFlow<AdminHomeUiState> {
//            return _uiState
//        }
//
//        override fun update(function: (AdminHomeUiState) -> AdminHomeUiState) {
//            TODO("Not yet implemented")
//        }
//    }
//}