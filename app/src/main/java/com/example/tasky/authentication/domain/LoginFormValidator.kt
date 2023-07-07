package com.example.tasky.authentication.domain

import com.example.tasky.core.domain.EmailValidator
import javax.inject.Inject

data class LoginFormValidator @Inject constructor(
    val emailValidator: EmailValidator,
    val passwordValidator: PasswordValidator
)
