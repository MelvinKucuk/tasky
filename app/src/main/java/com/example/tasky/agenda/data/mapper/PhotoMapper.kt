package com.example.tasky.agenda.data.mapper

import com.example.tasky.agenda.data.local.model.PhotoEntity
import com.example.tasky.agenda.data.remote.model.PhotoResponse
import com.example.tasky.agenda.domain.model.AgendaPhoto

fun PhotoResponse.toDomain() =
    AgendaPhoto.Remote(url = url, key = key)

fun AgendaPhoto.toEntity(eventId: String) =
    PhotoEntity(
        url = url,
        eventId = eventId,
        key = key
    )

fun PhotoEntity.toDomain() =
    AgendaPhoto.Local(url = url)