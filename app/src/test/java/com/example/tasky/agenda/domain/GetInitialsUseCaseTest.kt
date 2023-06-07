package com.example.tasky.agenda.domain

import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class GetInitialsUseCaseTest {

    private lateinit var getInitials: GetInitialsUseCase

    @Before
    fun setUp() {
        getInitials = GetInitialsUseCase()
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

        val result = getInitials(givenName)

        assertEquals("ME", result)
    }

    @Test
    fun `check initials when a name and surname`() {
        val givenName = "Melvin Kucuk"

        val result = getInitials(givenName)

        assertEquals("MK", result)
    }
}