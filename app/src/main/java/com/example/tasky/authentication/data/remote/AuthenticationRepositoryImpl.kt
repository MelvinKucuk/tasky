package com.example.tasky.authentication.data.remote

import com.example.tasky.authentication.domain.AuthenticationRepository
import com.example.tasky.core.data.Resource
import com.example.tasky.core.data.remote.safeApiCall
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val loginService: LoginService
) : AuthenticationRepository {

    override suspend fun login(email: String, password: String): Resource<LoginResponse> {
        return safeApiCall {
            loginService.login(
                LoginRequestBody(
                    email = email,
                    password = password
                )
            )
        }
    }
}