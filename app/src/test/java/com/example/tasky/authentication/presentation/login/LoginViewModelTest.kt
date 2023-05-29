package com.example.tasky.authentication.presentation.login

import com.example.tasky.MainCoroutineRule
import com.example.tasky.authentication.data.remote.LoginResponse
import com.example.tasky.authentication.domain.AuthenticationRepository
import com.example.tasky.authentication.domain.EmailValidator
import com.example.tasky.authentication.presentation.login.viewmodel.LoginEvent
import com.example.tasky.authentication.presentation.login.viewmodel.LoginState
import com.example.tasky.authentication.presentation.login.viewmodel.LoginViewModel
import com.example.tasky.core.data.Resource
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var viewModel: LoginViewModel

    private val emailValidator: EmailValidator = mockk()

    private val loginRepository: AuthenticationRepository = mockk()

    @Before
    fun setUp() {
        viewModel = LoginViewModel(
            emailValidator = emailValidator,
            authenticationRepository = loginRepository
        )
        every { emailValidator.validateEmail(any()) } returns true
    }

    @Test
    fun `update state on email value change and email is not valid`() {
        every { emailValidator.validateEmail(any()) } returns false

        val givenEmailValue = "juan"

        viewModel.onEvent(
            LoginEvent.OnEmailValueChanged(
                givenEmailValue
            )
        )

        val expectedValue = LoginState().copy(
            emailValue = givenEmailValue,
            isValidEmail = false
        )

        assertEquals(expectedValue, viewModel.state)
    }

    @Test
    fun `update state on email value change and email is valid`() {
        every { emailValidator.validateEmail(any()) } returns true

        viewModel.onEvent(
            LoginEvent.OnEmailValueChanged(
                emailValue
            )
        )

        val expectedValue = LoginState().copy(
            emailValue = emailValue,
            isValidEmail = true
        )

        assertEquals(expectedValue, viewModel.state)
    }

    @Test
    fun `update state on password value change`() {
        viewModel.onEvent(
            LoginEvent.OnPasswordValueChanged(
                passwordValue
            )
        )

        val expectedValue = LoginState().copy(
            passwordValue = passwordValue,
        )

        assertEquals(expectedValue, viewModel.state)
    }

    @Test
    fun `update state on show password value change`() {
        val givenShowPasswordValue = true

        viewModel.onEvent(
            LoginEvent.OnShowPasswordValueChanged(
                showPassword = givenShowPasswordValue
            )
        )

        val expectedValue = LoginState().copy(
            showPassword = givenShowPasswordValue,
        )

        assertEquals(expectedValue, viewModel.state)
    }

    @Test
    fun `update state when login is successfull`() {
        setEmailState()
        setPasswordState()
        val expectedResult = Resource.Success(
            LoginResponse(
                "1234",
                "111",
                "Juan Perez"
            )
        )

        coEvery { loginRepository.login(any(), any()) } returns expectedResult

        viewModel.onEvent(
            LoginEvent.OnLoginClicked
        )

        val expectedValue = LoginState().copy(
            emailValue = emailValue,
            passwordValue = passwordValue,
            onLoginSucceed = true,
            isValidEmail = true
        )

        coroutineRule.dispatcher.scheduler.advanceUntilIdle()

        assertEquals(expectedValue, viewModel.state)
    }

    @Test
    fun `update state when login gives error`() {
        val errorMessage = "Request failed"
        setEmailState()
        setPasswordState()
        val expectedResult = Resource.Error<LoginResponse>(
            errorMessage = errorMessage
        )

        coEvery { loginRepository.login(any(), any()) } returns expectedResult

        viewModel.onEvent(
            LoginEvent.OnLoginClicked
        )

        val expectedValue = LoginState().copy(
            emailValue = emailValue,
            passwordValue = passwordValue,
            isValidEmail = true,
            errorMessage = errorMessage,
        )

        coroutineRule.dispatcher.scheduler.advanceUntilIdle()

        assertEquals(expectedValue, viewModel.state)
    }

    private fun setEmailState() {
        viewModel.onEvent(
            LoginEvent.OnEmailValueChanged(
                emailValue
            )
        )
    }

    private fun setPasswordState() {
        viewModel.onEvent(
            LoginEvent.OnPasswordValueChanged(
                passwordValue
            )
        )
    }

    companion object {
        private const val emailValue = "juanperez@gmail.com"
        private const val passwordValue = "Test12345"
    }

}