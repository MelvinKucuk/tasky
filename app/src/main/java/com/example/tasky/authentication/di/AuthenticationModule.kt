package com.example.tasky.authentication.di

import com.example.tasky.authentication.data.EmailValidatorImpl
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.tasky.authentication.data.local.UserCacheImpl
import com.example.tasky.authentication.data.remote.AuthenticationRepositoryImpl
import com.example.tasky.authentication.data.remote.LoginService
import com.example.tasky.authentication.domain.AuthenticationRepository
import com.example.tasky.authentication.domain.EmailValidator
import com.example.tasky.authentication.domain.UserCache
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
class AuthenticationModule {

    @Provides
    fun providesSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("tasky_cache", MODE_PRIVATE)

    @Provides
    fun providesLoginRequest(retrofit: Retrofit): LoginService {
        return retrofit.create(LoginService::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthenticationModuleBinds {

    @Binds
    abstract fun bindsEmailValidator(emailValidatorImpl: EmailValidatorImpl): EmailValidator

    @Binds
    abstract fun bindsAuthenticationRepository(authenticationRepositoryImpl: AuthenticationRepositoryImpl): AuthenticationRepository

    @Binds
    abstract fun bindsUserCache(userCacheImpl: UserCacheImpl): UserCache
}