package com.example.tasky.authentication.domain

import junit.framework.TestCase.assertFalse
import org.junit.Before
import org.junit.Test

class NameValidatorTest {

    private lateinit var nameValidator: NameValidator

    @Before
    fun setUp() {
        nameValidator = NameValidator()
    }

    @Test
    fun `validate that it fails when the name has less than 4 characters`() {
        val result = nameValidator.validateName("pep")

        assertFalse(result)
    }

    @Test
    fun `validate that it fails when the name has more than 50 characters`() {
        val result =
            nameValidator.validateName("pepepepepepepepepepepepepepepepepepepepepepepepepepepepepepepepepepepepepepepepe")

        assertFalse(result)
    }

    @Test
    fun `validate that it succeed when the name has is in between 4 and 50 characters`() {
        val result = nameValidator.validateName("Juancito Perez")

        assert(result)
    }
}