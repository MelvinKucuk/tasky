package com.example.tasky.agenda.domain

import junit.framework.TestCase.assertEquals
import org.junit.Test

class GetInitialsTest {

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