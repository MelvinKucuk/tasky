package com.example.tasky.authentication.domain

import junit.framework.TestCase.assertFalse
import org.junit.Before
import org.junit.Test

class PasswordValidatorTest {

    private lateinit var passwordValidator: PasswordValidator

    @Before
    fun setUp() {
        passwordValidator = PasswordValidator()
    }

    @Test
    fun `password validation fails because it has no 9 character length`() {
        val result = passwordValidator.validatePassword("1234")

        assertFalse(result)
    }

    @Test
    fun `password validation fails because it has no letters`() {
        val result = passwordValidator.validatePassword("123456789")

        assertFalse(result)
    }

    @Test
    fun `password validation fails because it has no uppercase letters`() {
        val result = passwordValidator.validatePassword("123456789a")

        assertFalse(result)
    }

    @Test
    fun `password succeed because it has at least 9 characters, 1 digit, 1 uppercase letter, 1 lowercase letter`() {
        val result = passwordValidator.validatePassword("123456789aA")

        assert(result)
    }
}