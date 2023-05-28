package com.example.tasky.authentication.di

import com.example.tasky.authentication.data.remote.AuthenticationRepositoryImpl
import com.example.tasky.authentication.data.remote.LoginService
import com.example.tasky.authentication.domain.AuthenticationRepository
import com.example.tasky.authentication.domain.EmailValidator
import com.example.tasky.authentication.domain.EmailValidatorImpl
import com.example.tasky.authentication.domain.usecase.ValidateEmailUseCase
import com.example.tasky.authentication.domain.usecase.ValidateEmailUseCaseImpl
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
}

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthenticationModuleBinds {

    @Binds
    abstract fun bindsEmailUseCase(useCaseImpl: ValidateEmailUseCaseImpl): ValidateEmailUseCase

    @Binds
    abstract fun bindsEmailValidator(emailValidatorImpl: EmailValidatorImpl): EmailValidator

    @Binds
    abstract fun bindsAuthenticationRepository(authenticationRepositoryImpl: AuthenticationRepositoryImpl): AuthenticationRepository
}