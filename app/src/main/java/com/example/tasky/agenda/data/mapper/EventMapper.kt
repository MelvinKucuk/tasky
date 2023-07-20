package com.example.tasky.agenda.data.mapper

import com.example.tasky.agenda.data.local.model.EventEntity
import com.example.tasky.agenda.data.local.model.relations.EventWithAttendeesWithPhotos
import com.example.tasky.agenda.data.remote.model.CreateEventDto
import com.example.tasky.agenda.data.remote.model.EventResponse
import com.example.tasky.agenda.domain.model.AgendaItem
import com.example.tasky.agenda.domain.model.AgendaPhoto
import com.example.tasky.agenda.domain.model.Attendee

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

fun EventResponse.toEntity() =
    EventEntity(
        id = id,
        title = title,
        description = description,
        from = from,
        to = to,
        remindAt = remindAt,
        host = host,
        isUserEventCreator = isUserEventCreator,
    )

fun AgendaItem.Event.toEntity() =
    EventEntity(
        id = id,
        title = title,
        description = description,
        from = from,
        to = to,
        remindAt = remindAt,
        host = host,
        isUserEventCreator = isUserEventCreator,
    )

fun EventEntity.toDomain(attendees: List<Attendee>, photos: List<AgendaPhoto>) =
    AgendaItem.Event(
        id = id,
        title = title,
        description = description,
        from = from,
        to = to,
        remindAt = remindAt,
        host = host,
        isUserEventCreator = isUserEventCreator,
        attendees = attendees,
        photos = photos
    )

fun EventWithAttendeesWithPhotos.toDomain(): AgendaItem.Event {
    val attendees = this.attendees.map { it.toDomain() }
    val photos = this.photos.map { it.toDomain() }
    return event.toDomain(attendees, photos)
}

fun AgendaItem.Event.toCreateDto(): CreateEventDto {
    return CreateEventDto(
        id = id,
        title = title,
        description = description,
        from = from,
        to = to,
        remindAt = remindAt,
        attendeeIds = attendees.map { it.userId }
    )
}