package com.example.adminhome

import androidx.lifecycle.ViewModel
import com.example.adminhome.core.FlowWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AdminHomeViewModel @Inject constructor(private val flowWrapper: FlowWrapper) : ViewModel() {

    fun state() = flowWrapper.state()
}