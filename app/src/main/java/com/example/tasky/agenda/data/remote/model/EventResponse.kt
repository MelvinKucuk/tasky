package com.example.tasky.agenda.data.remote.model

import com.squareup.moshi.Json

data class EventResponse(
    @Json(name = "id")
    val id: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "from")
    val from: Long,
    @Json(name = "to")
    val to: Long,
    @Json(name = "remindAt")
    val remindAt: Long,
    @Json(name = "host")
    val host: String,
    @Json(name = "isUserEventCreator")
    val isUserEventCreator: Boolean,
    @Json(name = "attendees")
    val attendees: List<AttendeeResponse>,
    @Json(name = "photos")
    val photos: List<PhotoResponse>
)
