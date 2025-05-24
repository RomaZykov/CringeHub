package com.example.cringehub.admin.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.SyncScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

interface MainActivityViewModel {

    fun init()

    @HiltViewModel
    class Base @Inject constructor(
        private val syncScheduler: SyncScheduler
    ) : ViewModel(), MainActivityViewModel {
        override fun init() {
            viewModelScope.launch {
                syncScheduler.scheduleSyncLatestWork()
            }
        }
    }
}
