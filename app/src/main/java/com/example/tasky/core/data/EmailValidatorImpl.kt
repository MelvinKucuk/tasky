package com.example.tasky.core.data

import android.util.Patterns
import com.example.tasky.core.domain.EmailValidator
import javax.inject.Inject

class EmailValidatorImpl @Inject constructor() : EmailValidator {
    override operator fun invoke(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()
}