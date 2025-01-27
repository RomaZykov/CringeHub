package com.example.common.core

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

interface FlowWrapper<T> {

    fun setValue(newState: T)

    fun state(): StateFlow<T>

    fun update(function: (T) -> T)

    abstract class AbstractFlowWrapper<T : Any>(initialValue: T) : FlowWrapper<T> {
        private val _uiState: MutableStateFlow<T> = MutableStateFlow(initialValue)

        override fun state(): StateFlow<T> = _uiState.asStateFlow()

        override fun setValue(newState: T) {
            _uiState.value = newState
        }

        override fun update(function: (T) -> T) {
            _uiState.value = function(_uiState.value)
        }
    }

    class Base<T : Any> @Inject constructor(initialValue: T) : AbstractFlowWrapper<T>(initialValue)
}
