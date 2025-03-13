package com.example.adminauth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.adminauth.model.ErrorUi
import com.example.adminauth.model.InitialUi
import com.example.adminnavigation.AdminHomeRouteProvider
import com.example.adminnavigation.navigateIfResumed
import com.example.domain.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

interface AdminAuthViewModel {

    fun adminAuthScreenUiStateFlow(): StateFlow<AdminAuthUiState>

    fun onSignInClick(email: String, password: String, navController: NavController)

    @HiltViewModel
    class Base @Inject constructor(
        private val adminAuthRepository: AuthRepository.AdminAuthRepository,
        private val adminHomeRouteProvider: AdminHomeRouteProvider
    ) : ViewModel(), AdminAuthViewModel {

        private val _uiState = MutableStateFlow<AdminAuthUiState>(InitialUi)

        override fun adminAuthScreenUiStateFlow(): StateFlow<AdminAuthUiState> = _uiState

        override fun onSignInClick(email: String, password: String, navController: NavController) {
            viewModelScope.launch {
                _uiState.value = InitialUi
                adminAuthRepository.signInWithEmail(email, password)
                    .onSuccess {
                        navController.navigateIfResumed(adminHomeRouteProvider.route())
                    }.onFailure {
                        _uiState.value = ErrorUi
                    }
            }
        }
    }
}
