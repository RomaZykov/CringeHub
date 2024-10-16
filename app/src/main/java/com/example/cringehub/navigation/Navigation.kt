package com.example.cringehub.navigation

import com.example.cringehub.ui.MainActivityUiState
import kotlinx.coroutines.flow.StateFlow

interface Navigation {


    class Base(private val uiState: StateFlow<MainActivityUiState> = ) {

    }
}