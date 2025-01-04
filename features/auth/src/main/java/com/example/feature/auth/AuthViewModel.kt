package com.example.feature.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repositories.auth.AuthRepository
import com.example.feature.auth.core.FlowWrapper
import com.example.feature.auth.core.ViewModelActions
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
            flowWrapper.setValue(AuthUiState.Initial)
            repository.signInWithGoogle(activityContext)
                .onSuccess {
                    flowWrapper.setValue(AuthUiState.Success)
                }.onFailure {
                    flowWrapper.setValue(AuthUiState.Error(it.message.toString()))
                }
        }
    }
}
