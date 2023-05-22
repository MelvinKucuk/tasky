package com.example.tasky.authentication.presentation.login.viewmodel

class LoginViewModel {
}

data class LoginState(
    val isValidEmail: Boolean = false,
    val isLoading: Boolean = false,
    val emailValue: String = "",
    val passwordValue: String = "",
    val showPassword: Boolean = false
)

sealed class LoginEvent {
    data class OnEmailValueChanged(val emailValue: String) : LoginEvent()
    data class OnPasswordValueChanged(val passwordValue: String) : LoginEvent()
    data class OnShowPasswordValueChanged(val showPassword: Boolean) : LoginEvent()
    object OnLoginClicked : LoginEvent()
    object OnSignUpClicked : LoginEvent()
}
