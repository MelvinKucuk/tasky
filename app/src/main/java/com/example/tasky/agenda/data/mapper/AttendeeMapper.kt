package com.example.tasky.agenda.data.mapper

import com.example.tasky.agenda.data.local.model.AttendeeEntity
import com.example.tasky.agenda.data.remote.model.AttendeeResponse
import com.example.tasky.core.domain.model.Attendee

fun AttendeeResponse.toDomain() =
    Attendee(
        email = email,
        fullName = fullName,
        userId = userId,
        eventId = eventId,
        isGoing = isGoing,
        remindAt = remindAt,
    )

fun AttendeeEntity.toDomain() =
    Attendee(
        email = email,
        fullName = fullName,
        userId = userId,
        eventId = eventId,
        isGoing = isGoing,
        remindAt = remindAt,
    )

fun Attendee.toEntity() =
    AttendeeEntity(
        email = email,
        fullName = fullName,
        userId = userId,
        eventId = eventId,
        isGoing = isGoing,
        remindAt = remindAt,
    )