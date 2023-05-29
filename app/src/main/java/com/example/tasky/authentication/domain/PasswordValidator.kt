package com.example.tasky.authentication.domain

import javax.inject.Inject

class PasswordValidator @Inject constructor() {

    private val passwordRegex = Regex("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{9,}$")
    fun validatePassword(password: String): Boolean {
        return password.matches(passwordRegex)
    }
}