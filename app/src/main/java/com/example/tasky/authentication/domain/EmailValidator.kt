package com.example.tasky.authentication.domain

import android.util.Patterns
import javax.inject.Inject

interface EmailValidator {
    fun validateEmail(email: String): Boolean
}

class EmailValidatorImpl @Inject constructor() : EmailValidator {
    override fun validateEmail(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).find()
}