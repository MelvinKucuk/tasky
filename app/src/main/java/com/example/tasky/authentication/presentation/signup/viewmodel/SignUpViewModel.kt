package com.example.tasky.authentication.presentation.signup.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(SignUpState())
        private set

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.OnEmailValueChanged -> {
                state = state.copy(emailValue = event.emailValue)
            }

            is SignUpEvent.OnNameValueChanged -> {
                state = state.copy(nameValue = event.nameValue)
            }

            is SignUpEvent.OnPasswordValueChanged -> {
                state = state.copy(passwordValue = event.passwordValue)
            }

            is SignUpEvent.OnShowPasswordValueChanged -> {
                state = state.copy(showPassword = event.showPassword)
            }

            SignUpEvent.OnSignUpClicked -> {
                // TODO
                state = state.copy(onSignUpSucceed = true)
            }

            SignUpEvent.BackNavigated -> state = state.copy(navigateBack = null)
            SignUpEvent.ErrorShown -> state = state.copy(errorMessage = null)
            SignUpEvent.OnBackClicked -> state = state.copy(navigateBack = null)
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
}