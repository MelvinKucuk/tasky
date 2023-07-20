package com.example.tasky.agenda.data.local.model.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.tasky.agenda.data.local.model.AttendeeEntity
import com.example.tasky.agenda.data.local.model.EventEntity
import com.example.tasky.agenda.data.local.model.PhotoEntity

data class EventWithAttendeesWithPhotos(
    @Embedded val event: EventEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId",
        associateBy = Junction(EventAttendeesCrossRef::class)
    )
    val attendees: List<AttendeeEntity>,

    @Relation(
        entity = PhotoEntity::class,
        parentColumn = "id",
        entityColumn = "eventId",
    )
    val photos: List<PhotoEntity>
)
