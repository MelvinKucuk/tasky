package com.example.tasky.authentication.data.remote.login

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("/login")
    suspend fun login(@Body body: LoginRequestBody): Response<LoginResponse>
}