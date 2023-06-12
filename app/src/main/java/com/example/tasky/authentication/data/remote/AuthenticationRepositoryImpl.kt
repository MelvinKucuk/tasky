package com.example.tasky.authentication.data.remote

import com.example.tasky.authentication.data.mapper.toUser
import com.example.tasky.authentication.data.remote.login.LoginRequestBody
import com.example.tasky.authentication.data.remote.login.LoginResponse
import com.example.tasky.authentication.data.remote.login.LoginService
import com.example.tasky.authentication.data.remote.signup.SignUpRequest
import com.example.tasky.authentication.data.remote.signup.SignUpService
import com.example.tasky.authentication.domain.AuthenticationRepository
import com.example.tasky.authentication.domain.UserCache
import com.example.tasky.core.data.Resource
import com.example.tasky.core.data.remote.safeApiCall
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val loginService: LoginService,
    private val signUpService: SignUpService,
    private val userCache: UserCache,
) : AuthenticationRepository {

    override suspend fun login(email: String, password: String): Resource<LoginResponse> {
        val result = safeApiCall {
            loginService.login(
                LoginRequestBody(
                    email = email,
                    password = password
                )
            )
        }

        if (result is Resource.Success) {
            userCache.saveUser(result.data.toUser())
        }

        return result
    }

    override suspend fun registerUser(fullName: String, email: String, password: String) =
        safeApiCall {
            signUpService.registerUser(
                SignUpRequest(
                    fullName = fullName,
                    email = email,
                    password = password
                )
            )
        }

    override suspend fun checkAuthentication() = loginService.checkAuthentication()
}