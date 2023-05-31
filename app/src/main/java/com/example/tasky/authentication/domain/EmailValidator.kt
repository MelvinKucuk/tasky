package com.example.tasky.authentication.domain

interface EmailValidator {
    operator fun invoke(email: String): Boolean
}