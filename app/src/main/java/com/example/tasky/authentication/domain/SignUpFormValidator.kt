package com.example.tasky.authentication.domain

import com.example.tasky.core.domain.EmailValidator
import javax.inject.Inject

data class SignUpFormValidator @Inject constructor(
    val emailValidator: EmailValidator,
    val nameValidator: NameValidator,
    val passwordValidator: PasswordValidator
)
