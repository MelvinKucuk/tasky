package com.example.tasky.authentication.data.remote.signup

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpService {

    @POST("/register")
    suspend fun registerUser(@Body signUpRequest: SignUpRequest): Response<ResponseBody>
}