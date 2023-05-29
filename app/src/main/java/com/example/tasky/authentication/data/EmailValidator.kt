package com.example.tasky.authentication.data

import android.util.Patterns
import javax.inject.Inject

class EmailValidator @Inject constructor() {
    fun validateEmail(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).find()
}