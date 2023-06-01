package com.example.tasky.authentication.presentation.signup.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasky.authentication.domain.AuthenticationRepository
import com.example.tasky.authentication.domain.SignUpFormValidator
import com.example.tasky.core.data.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val formValidator: SignUpFormValidator,
    private val authenticationRepository: AuthenticationRepository,
) : ViewModel() {

    var state by mutableStateOf(SignUpState())
        private set

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.OnEmailValueChanged -> {
                val isValid = formValidator.emailValidator(event.emailValue)
                state = state.copy(
                    emailValue = event.emailValue,
                    isValidEmail = isValid
                )
            }

            is SignUpEvent.OnNameValueChanged -> {
                val isValid = formValidator.nameValidator(event.nameValue)
                state = state.copy(
                    nameValue = event.nameValue,
                    isValidName = isValid
                )
            }

            is SignUpEvent.OnPasswordValueChanged -> {
                state = state.copy(passwordValue = event.passwordValue)
            }

            is SignUpEvent.OnShowPasswordValueChanged -> {
                state = state.copy(showPassword = event.showPassword)
            }

            SignUpEvent.OnSignUpClicked -> {
                if (!state.isLoading) {
                    if (!state.isValidName) {
                        state = state.copy(errorMessage = "Invalid name")
                        return
                    }

                    if (!state.isValidEmail) {
                        state = state.copy(errorMessage = "Invalid email")
                        return
                    }

                    if (!formValidator.passwordValidator(state.passwordValue)) {
                        state = state.copy(
                            errorMessage = "Invalid password. It must be at least " +
                                    "9 characters long, have 1 lower case, 1 upper case, and 1 number"
                        )
                        return
                    }

                    state = state.copy(isLoading = true)

                    viewModelScope.launch {
                        val result = authenticationRepository.registerUser(
                            fullName = state.nameValue,
                            email = state.emailValue,
                            password = state.passwordValue
                        )

                        state = when (result) {
                            is Resource.Success -> {
                                state.copy(
                                    onSignUpSucceed = true,
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
            }

            SignUpEvent.OnBackClicked -> state = state.copy(navigateBack = true)
            SignUpEvent.BackNavigated -> state = state.copy(navigateBack = null)
            SignUpEvent.ErrorShown -> state = state.copy(errorMessage = null)
            SignUpEvent.SignUpNavigated -> state = state.copy(onSignUpSucceed = null)
        }
    }
}

data class SignUpState(
    val isValidEmail: Boolean = false,
    val isValidName: Boolean = false,
    val emailValue: String = "",
    val nameValue: String = "",
    val passwordValue: String = "",
    val showPassword: Boolean = false,
    val isLoading: Boolean = false,
    val onSignUpSucceed: Boolean? = null,
    val errorMessage: String? = null,
    val navigateBack: Boolean? = null
)

sealed class SignUpEvent {
    data class OnEmailValueChanged(val emailValue: String) : SignUpEvent()
    data class OnNameValueChanged(val nameValue: String) : SignUpEvent()
    data class OnPasswordValueChanged(val passwordValue: String) : SignUpEvent()
    data class OnShowPasswordValueChanged(val showPassword: Boolean) : SignUpEvent()
    object OnSignUpClicked : SignUpEvent()
    object OnBackClicked : SignUpEvent()
    object ErrorShown : SignUpEvent()
    object BackNavigated : SignUpEvent()
    object SignUpNavigated : SignUpEvent()
}