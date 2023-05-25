package com.example.tasky.authentication.domain.usecase

import com.example.tasky.authentication.domain.EmailValidator
import javax.inject.Inject

interface ValidateEmailUseCase {
    operator fun invoke(email: String): Boolean
}

class ValidateEmailUseCaseImpl @Inject constructor(
    private val emailValidator: EmailValidator
) : ValidateEmailUseCase {
    override operator fun invoke(email: String) =
        emailValidator.validateEmail(email)
}