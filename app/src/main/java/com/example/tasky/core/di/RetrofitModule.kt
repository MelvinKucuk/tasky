package com.example.tasky.core.di

import com.example.tasky.BuildConfig
import com.example.tasky.core.data.remote.interceptor.JWTInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Singleton
    @Provides
    fun providesOkHttpClient(jwtInterceptor: JWTInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .addInterceptor {
                val request = it
                    .request()
                    .newBuilder()
                    .addHeader(HEADER, BuildConfig.API_KEY)
                    .build()
                it.proceed(request)
            }
            .addInterceptor(jwtInterceptor)
            .build()


    @Singleton
    @Provides
    fun providesRetrofit(client: OkHttpClient): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        return Retrofit
            .Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
    }

    companion object {
        private const val HEADER = "x-api-key"
    }
}