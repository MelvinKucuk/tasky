package com.example.tasky.core.data.remote.interceptor

import com.example.tasky.authentication.domain.UserCache
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class JWTInterceptor @Inject constructor(
    private val userCache: UserCache
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = userCache.getUser()?.token
        val request = chain.request().newBuilder()
        token?.let {
            request.addHeader("Authorization", "Bearer $it")
        }
        return chain.proceed(request.build())
    }
}