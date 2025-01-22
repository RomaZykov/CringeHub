package com.example.adminauth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminauth.core.FlowWrapper
import com.example.adminauth.core.ViewModelActions
import com.example.domain.repositories.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminAuthViewModel @Inject constructor(
    private val flowWrapper: FlowWrapper,
    private val adminAuthRepository: AuthRepository.AdminAuthRepository
) : ViewModel(), ViewModelActions {

    fun state() = flowWrapper.state()

    override fun onSignInClick(email: String, password: String) {
        viewModelScope.launch {
            flowWrapper.setValue(AdminAuthUiState.Initial)
            adminAuthRepository.signInWithEmail(email, password)
                .onSuccess {
                    flowWrapper.setValue(AdminAuthUiState.Success)
                }.onFailure {
                    flowWrapper.setValue(AdminAuthUiState.Error)
                }
        }
    }
}