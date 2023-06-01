package com.example.tasky.authentication.domain

import javax.inject.Inject

data class LoginFormValidator @Inject constructor(
    val emailValidator: EmailValidator,
    val passwordValidator: PasswordValidator
)
