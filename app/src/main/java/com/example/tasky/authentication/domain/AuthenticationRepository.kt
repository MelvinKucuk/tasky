package com.example.tasky.authentication.domain

import com.example.tasky.authentication.data.remote.login.LoginResponse
import com.example.tasky.core.data.Resource
import okhttp3.ResponseBody

interface AuthenticationRepository {
    suspend fun login(email: String, password: String): Resource<LoginResponse>
    suspend fun registerUser(
        fullName: String,
        email: String,
        password: String
    ): Resource<ResponseBody>
}