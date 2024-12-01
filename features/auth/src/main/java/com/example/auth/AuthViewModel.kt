package com.example.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.core.FlowWrapper
import com.example.auth.core.ViewModelActions
import com.example.domain.repositories.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val flowWrapper: FlowWrapper,
    private val repository: AuthRepository
) : ViewModel(), ViewModelActions {

    fun state() = flowWrapper.state()

    override fun onSignInClick(activityContext: Context) {
        viewModelScope.launch {
            try {
                repository.signInWithGoogle(activityContext)
//                flowWrapper.setValue(AuthUiState.Success)
            } catch (e: Exception) {
                flowWrapper.setValue(AuthUiState.Error(e.message.toString()))
            }
        }
    }
}
