package com.example.feature.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature.auth.core.FlowWrapper
import com.example.feature.auth.core.ViewModelActions
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
                repository.signInWithGoogle(activityContext).onSuccess {
                    flowWrapper.setValue(AuthUiState.Success)
                }
            } catch (e: Exception) {
                flowWrapper.setValue(AuthUiState.Error(e.message.toString()))
            }
        }
    }
}
