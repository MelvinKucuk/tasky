package com.example.tasky.authentication.data.remote.signup

import com.squareup.moshi.Json

data class SignUpRequest(
    @Json(name = "fullName") val fullName: String,
    @Json(name = "email") val email: String,
    @Json(name = "password") val password: String
)
