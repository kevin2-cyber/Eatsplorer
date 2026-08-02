package com.kimikevin.eatsplorer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {
    private val _isDataReady = MutableStateFlow(false)
    val isDataReady: StateFlow<Boolean> = _isDataReady.asStateFlow()

    init {
        checkAppInitialization()
    }

    private fun checkAppInitialization() {
        viewModelScope.launch {
            // Simulate heavy initialization (e.g., checking user session, warming cache)
            delay(1500)
            _isDataReady.value = true
        }
    }
}
