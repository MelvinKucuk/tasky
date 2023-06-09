package com.example.tasky.core.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasky.core.data.remote.SplashService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val splashService: SplashService
) : ViewModel() {

    var state by mutableStateOf(SplashState())
        private set

    fun checkAuthentication() {
        viewModelScope.launch {
            val result = splashService.checkAuthentication()

            state = state.copy(
                isLoading = false,
                navigateToAgenda = result.isSuccessful
            )
        }
    }
}

data class SplashState(
    val isLoading: Boolean = true,
    val navigateToAgenda: Boolean? = null
)