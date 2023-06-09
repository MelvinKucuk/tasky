package com.example.tasky.authentication.data.remote.login

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginService {

    @POST("/login")
    suspend fun login(@Body body: LoginRequestBody): Response<LoginResponse>

    @GET("/authenticate")
    suspend fun checkAuthentication(): Response<ResponseBody>
}