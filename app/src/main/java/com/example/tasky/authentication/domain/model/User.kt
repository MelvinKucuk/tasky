package com.example.tasky.authentication.domain.model

data class User(
    val token: String,
    val userId: String,
    val fullName: String
)
