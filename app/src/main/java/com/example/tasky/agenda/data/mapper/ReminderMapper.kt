package com.example.tasky.agenda.data.mapper

import com.example.tasky.agenda.data.local.model.ReminderEntity
import com.example.tasky.agenda.data.remote.model.ReminderResponse
import com.example.tasky.agenda.domain.model.AgendaItem

fun ReminderResponse.toDomain() =
    AgendaItem.Reminder(
        id = id,
        title = title,
        description = description,
        time = time,
        remindAt = remindAt,
    )

fun ReminderResponse.toEntity() =
    ReminderEntity(
        id = id,
        title = title,
        description = description,
        time = time,
        remindAt = remindAt
    )

fun ReminderEntity.toDomain() =
    AgendaItem.Reminder(
        id = id,
        title = title,
        description = description,
        time = time,
        remindAt = remindAt,
    )