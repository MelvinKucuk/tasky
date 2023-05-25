package com.example.tasky.authentication.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequestBody(
    @Json(name = "email") val email: String,
    @Json(name = "password") val password: String
)

data class LoginResponse(
    @Json(name = "token") val token: String,
    @Json(name = "userId") val userId: String,
    @Json(name = "fullName") val fullName: String
)