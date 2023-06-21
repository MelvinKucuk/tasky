package com.example.tasky.core.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasky.agenda.data.local.AgendaDatabase
import com.example.tasky.authentication.domain.AuthenticationRepository
import com.example.tasky.authentication.domain.UserCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val userCache: UserCache,
    private val dataBase: AgendaDatabase
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

    fun logout() = viewModelScope.launch(Dispatchers.IO) {
        userCache.deleteUser()
        dataBase.clearAllTables()
    }
}

data class SplashState(
    val isLoading: Boolean = true,
    val isLoggedIn: Boolean? = null
)