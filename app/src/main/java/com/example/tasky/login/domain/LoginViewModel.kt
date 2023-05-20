package com.example.tasky.login.domain

import androidx.compose.ui.text.input.TextFieldValue

class LoginViewModel {
}

data class LoginState(
    val isValidEmail: Boolean,
    val isLoading: Boolean,
    val emailValue: TextFieldValue,
    val passwordValue: TextFieldValue,
    val showPassword: Boolean
)

sealed class LoginEvent {
    data class OnEmailValueChanged(val emailValue: TextFieldValue) : LoginEvent()
    data class OnPasswordValueChanged(val passwordValue: TextFieldValue) : LoginEvent()
    data class OnShowPasswordValueChanged(val showPassword: Boolean) : LoginEvent()
    object OnLoginClicked : LoginEvent()
    object OnSignUpClicked : LoginEvent()
}
