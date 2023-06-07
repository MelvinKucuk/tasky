package com.example.tasky.agenda.data.mapper

import com.example.tasky.agenda.data.remote.model.ReminderResponse
import com.example.tasky.agenda.domain.model.Agenda

fun ReminderResponse.toDomain() =
    Agenda.Reminder(
        id = id,
        title = title,
        description = description,
        time = time,
        remindAt = remindAt,
    )