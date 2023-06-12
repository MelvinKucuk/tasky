package com.example.tasky.core.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasky.authentication.domain.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    var state by mutableStateOf(SplashState())
        private set

    init {
        viewModelScope.launch {
            val result = authenticationRepository.checkAuthentication()

            state = state.copy(
                isLoading = false,
                isLoggedIn = result.isSuccessful
            )
        }
    }
}

data class SplashState(
    val isLoading: Boolean = true,
    val isLoggedIn: Boolean? = null
)