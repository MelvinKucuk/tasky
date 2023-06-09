package com.example.tasky.authentication.di

import com.example.tasky.authentication.data.local.UserCacheImpl
import com.example.tasky.authentication.data.remote.AuthenticationRepositoryImpl
import com.example.tasky.authentication.data.remote.login.LoginService
import com.example.tasky.authentication.data.remote.signup.SignUpService
import com.example.tasky.authentication.domain.AuthenticationRepository
import com.example.tasky.authentication.domain.UserCache
import com.example.tasky.core.data.EmailValidatorImpl
import com.example.tasky.core.domain.EmailValidator
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
class AuthenticationModule {

    @Provides
    fun providesLoginRequest(retrofit: Retrofit): LoginService {
        return retrofit.create(LoginService::class.java)
    }

    @Provides
    fun providesSignUpService(retrofit: Retrofit): SignUpService =
        retrofit.create(SignUpService::class.java)
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