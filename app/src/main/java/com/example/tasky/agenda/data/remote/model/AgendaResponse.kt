package com.example.tasky.agenda.data.remote.model

import com.squareup.moshi.Json

data class AgendaResponse(
    @Json(name = "events")
    val events: List<EventResponse>,
    @Json(name = "reminders")
    val reminders: List<ReminderResponse>,
    @Json(name = "tasks")
    val tasks: List<TaskResponse>
)
