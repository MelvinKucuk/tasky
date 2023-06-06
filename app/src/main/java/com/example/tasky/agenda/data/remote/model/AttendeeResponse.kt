package com.example.tasky.agenda.data.remote.model

import com.squareup.moshi.Json

data class AttendeeResponse(
    @Json(name = "email")
    val email: String,
    @Json(name = "fullName")
    val fullName: String,
    @Json(name = "userId")
    val userId: String,
    @Json(name = "eventId")
    val eventId: String?,
    @Json(name = "isGoing")
    val isGoing: Boolean?,
    @Json(name = "remindAt")
    val remindAt: Long?
)
