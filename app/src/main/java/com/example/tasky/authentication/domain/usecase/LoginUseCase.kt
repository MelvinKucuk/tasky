package com.example.tasky.authentication.domain.usecase

import javax.inject.Inject

data class LoginUseCase @Inject constructor(
    val validateEmailUseCase: ValidateEmailUseCase
)
