package com.example.tasky.authentication.presentation.login.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasky.authentication.domain.AuthenticationRepository
import com.example.tasky.authentication.domain.EmailValidator
import com.example.tasky.core.data.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val emailValidator: EmailValidator,
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailValueChanged -> {
                val isValid = emailValidator.validateEmail(event.emailValue)
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

                if (state.isLoading) return

                state = state.copy(isLoading = true)
                viewModelScope.launch {
                    val result = authenticationRepository.login(
                        email = state.emailValue,
                        password = state.passwordValue
                    )

                    state = when (result) {
                        is Resource.Success -> {
                            state.copy(
                                onLoginSucceed = true,
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
                }
            }

            LoginEvent.OnSignUpClicked -> {
                state = state.copy(navigateToSignUp = true)
            }

            LoginEvent.ErrorShown -> state = state.copy(errorMessage = null)
            LoginEvent.LoginNavigated -> state = state.copy(onLoginSucceed = null)
            LoginEvent.SignUpNavigated -> state = state.copy(navigateToSignUp = null)
        }
    }
}

data class LoginState(
    val isValidEmail: Boolean = false,
    val isLoading: Boolean = false,
    val emailValue: String = "",
    val passwordValue: String = "",
    val showPassword: Boolean = false,
    val errorMessage: String? = null,
    val onLoginSucceed: Boolean? = null,
    val navigateToSignUp: Boolean? = null
)

sealed class LoginEvent {
    data class OnEmailValueChanged(val emailValue: String) : LoginEvent()
    data class OnPasswordValueChanged(val passwordValue: String) : LoginEvent()
    data class OnShowPasswordValueChanged(val showPassword: Boolean) : LoginEvent()
    object OnLoginClicked : LoginEvent()
    object OnSignUpClicked : LoginEvent()
    object ErrorShown : LoginEvent()
    object LoginNavigated : LoginEvent()
    object SignUpNavigated : LoginEvent()
}
