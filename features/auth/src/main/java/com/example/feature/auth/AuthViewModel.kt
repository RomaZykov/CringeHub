package com.example.feature.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.core.FlowWrapper
import com.example.domain.repositories.AuthRepository
import com.example.feature.auth.core.ViewModelActions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val flowWrapper: FlowWrapper<AuthUiState>,
    private val authRepository: AuthRepository.Client
) : ViewModel(), ViewModelActions {

    fun state() = flowWrapper.state()

    override fun onSignInClick(activityContext: Context) {
        viewModelScope.launch {
            flowWrapper.setValue(AuthUiState.Initial)
            authRepository.signInWithGoogle(activityContext)
                .onSuccess {
                    flowWrapper.setValue(AuthUiState.Success)
                }.onFailure {
                    flowWrapper.setValue(AuthUiState.Error(it.message.toString()))
                }
        }
    }
}
