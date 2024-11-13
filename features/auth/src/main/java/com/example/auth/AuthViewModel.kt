package com.example.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.core.FlowWrapper
import com.example.domain.repositories.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val flowWrapper: FlowWrapper = FlowWrapper.Base(),
    private val repository: AuthRepository,
) : ViewModel() {

    fun state() = flowWrapper.state()

    fun updateState(newState: AuthUiState) = flowWrapper.setValue(newState)

    fun onSignInClick() {
        viewModelScope.launch {
            repository.signInWithGoogle()
        }
    }
}