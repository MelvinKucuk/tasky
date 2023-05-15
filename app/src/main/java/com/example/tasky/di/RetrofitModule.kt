package com.example.tasky.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Singleton
    fun providesOkHttpClient() =
        OkHttpClient
            .Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()


    @Singleton
    fun providesRetrofit(client: OkHttpClient) =
        Retrofit
            .Builder()
            .baseUrl("https://tasky.pl-coding.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
}