package com.example.tasky.agenda.domain

import junit.framework.TestCase.assertEquals
import org.junit.Test

class GetInitialsUseCaseTest {



    @Test
    fun `check empty result when input is empty`() {
        val givenName = ""

        val result = GetInitialsUseCase(givenName)

        assertEquals("", result)
    }

    @Test
    fun `check empty result when input is empty`() {
        val givenName = ""

        val result = getInitials(givenName)

        assertEquals("", result)
    }

    @Test
    fun `check initials when there is only a name and no surname`() {
        val givenName = "Melvin"

        val result = GetInitialsUseCase(givenName)

        assertEquals("ME", result)
    }

    @Test
    fun `check initials when a name and surname`() {
        val givenName = "Melvin Kucuk"

        val result = GetInitialsUseCase(givenName)

        assertEquals("MK", result)
    }

    @Test
    fun `check initials when the name has 3 names`() {
        val givenName = "Melvin Alex Kucuk"

        val result = GetInitialsUseCase(givenName)

        assertEquals("MK", result)
    }

    @Test
    fun `check initials when the name has more than 3 names`() {
        val givenName = "Melvin Alex Jacobo Kucuk"

        val result = GetInitialsUseCase(givenName)

        assertEquals("MK", result)
    }

    @Test
    fun `check initials when the name has 3 names`() {
        val givenName = "Melvin Alex Kucuk"

        val result = getInitials(givenName)

        assertEquals("MK", result)
    }

    @Test
    fun `check initials when the name has more than 3 names`() {
        val givenName = "Melvin Alex Jacobo Kucuk"

        val result = getInitials(givenName)

        assertEquals("MK", result)
    }
}