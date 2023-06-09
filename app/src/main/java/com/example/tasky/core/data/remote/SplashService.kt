package com.example.tasky.core.data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface SplashService {

    @GET("/authenticate")
    suspend fun checkAuthentication(): Response<ResponseBody>
}