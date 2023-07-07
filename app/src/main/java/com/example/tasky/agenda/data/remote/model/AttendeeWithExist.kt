package com.example.tasky.agenda.data.remote.model

import com.squareup.moshi.Json

data class AttendeeWithExist(
    @Json(name = "doesUserExist")
    val doesUserExist: Boolean,
    @Json(name = "attendee")
    val attendee: AttendeeResponse?
)
