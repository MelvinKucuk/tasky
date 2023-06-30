package com.example.tasky.core.domain.model

import com.example.tasky.agenda.domain.getInitials

data class Attendee(
    val email: String = "",
    val fullName: String = "",
    val userId: String = "",
    val eventId: String? = null,
    val isGoing: Boolean? = null,
    val remindAt: Long? = null,
    val isUserEventCreator: Boolean = false
) {
    val initials: String get() = getInitials(fullName)
}
