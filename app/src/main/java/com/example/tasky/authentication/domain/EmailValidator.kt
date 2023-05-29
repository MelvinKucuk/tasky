package com.example.tasky.authentication.domain

interface EmailValidator {
    fun validateEmail(email: String): Boolean
}