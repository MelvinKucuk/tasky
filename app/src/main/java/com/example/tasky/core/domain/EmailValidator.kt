package com.example.tasky.core.domain

interface EmailValidator {
    operator fun invoke(email: String): Boolean
}