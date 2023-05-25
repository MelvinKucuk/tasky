package com.example.tasky.authentication.data.remote

import com.example.tasky.core.data.Resource
import com.example.tasky.core.data.remote.BaseRepository
import javax.inject.Inject

interface LoginRepository {
    suspend fun login(email: String, password: String): Resource<LoginResponse>
}

class LoginRepositoryImpl @Inject constructor(
    private val loginService: LoginService
) : LoginRepository, BaseRepository() {

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