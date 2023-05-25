package com.example.tasky.authentication.presentation.login.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasky.authentication.data.remote.LoginRepository
import com.example.tasky.authentication.domain.usecase.LoginUseCase
import com.example.tasky.core.data.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val loginRepository: LoginRepository
) : ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    private var loginJob: Job? = null

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailValueChanged -> {
                val isValid = loginUseCase.validateEmailUseCase(event.emailValue)
                state = state.copy(isValidEmail = isValid, emailValue = event.emailValue)
            }

            is LoginEvent.OnPasswordValueChanged -> {
                state = state.copy(passwordValue = event.passwordValue)
            }

            is LoginEvent.OnShowPasswordValueChanged -> {
                state = state.copy(showPassword = !state.showPassword)
            }

            LoginEvent.OnLoginClicked -> {
                if (!state.isValidEmail) {
                    state = state.copy(errorMessage = "Invalid email")
                    return
                }

                if (loginJob != null) return

                state = state.copy(isLoading = true)
                loginJob = viewModelScope.launch {
                    val result = loginRepository.login(state.emailValue, state.passwordValue)

                    state = when (result) {
                        is Resource.Success -> {
                            state.copy(
                                errorMessage = result.data.fullName,
                                isLoading = false
                            )
                        }

                        is Resource.Error -> {
                            state.copy(
                                errorMessage = result.errorMessage,
                                isLoading = false
                            )
                        }
                    }
                    loginJob = null
                }
            }

            LoginEvent.OnSignUpClicked -> {
                state = state.copy(isLoading = false)
            }
        }
    }

    fun errorShown() {
        state = state.copy(errorMessage = "")
    }

}

data class LoginState(
    val isValidEmail: Boolean = false,
    val isLoading: Boolean = false,
    val emailValue: String = "",
    val passwordValue: String = "",
    val showPassword: Boolean = false,
    val errorMessage: String = ""
)

sealed class LoginEvent {
    data class OnEmailValueChanged(val emailValue: String) : LoginEvent()
    data class OnPasswordValueChanged(val passwordValue: String) : LoginEvent()
    data class OnShowPasswordValueChanged(val showPassword: Boolean) : LoginEvent()
    object OnLoginClicked : LoginEvent()
    object OnSignUpClicked : LoginEvent()
}
