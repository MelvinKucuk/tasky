package com.example.tasky.agenda.data.mapper

import com.example.tasky.agenda.data.remote.model.EventResponse
import com.example.tasky.agenda.domain.model.AgendaItem

fun EventResponse.toDomain() =
    AgendaItem.Event(
        id = id,
        title = title,
        description = description,
        from = from,
        to = to,
        remindAt = remindAt,
        host = host,
        isUserEventCreator = isUserEventCreator,
        attendees = attendees.map { it.toDomain() },
        photos = photos.map { it.toDomain() },
    )