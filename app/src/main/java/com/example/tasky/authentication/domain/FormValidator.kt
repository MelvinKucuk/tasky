package com.example.tasky.authentication.domain

import javax.inject.Inject

data class FormValidator @Inject constructor(
    val emailValidator: EmailValidator,
    val nameValidator: NameValidator,
    val passwordValidator: PasswordValidator
)
