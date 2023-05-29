package com.example.tasky.authentication.data

import android.util.Patterns
import com.example.tasky.authentication.domain.EmailValidator
import javax.inject.Inject

class EmailValidatorImpl @Inject constructor() : EmailValidator {
    override fun validateEmail(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).find()
}