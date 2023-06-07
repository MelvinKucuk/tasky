package com.example.tasky.agenda.domain.model

data class Attendee(
    val email: String = "",
    val fullName: String = "",
    val userId: String = "",
    val eventId: String? = null,
    val isGoing: Boolean? = null,
    val remindAt: Long? = null
)
