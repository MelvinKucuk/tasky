package com.example.tasky.authentication.domain

import com.example.tasky.authentication.data.remote.LoginResponse
import com.example.tasky.core.data.Resource

interface AuthenticationRepository {
    suspend fun login(email: String, password: String): Resource<LoginResponse>
}