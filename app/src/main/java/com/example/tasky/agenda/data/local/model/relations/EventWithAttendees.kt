package com.example.tasky.agenda.data.local.model.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.tasky.agenda.data.local.model.AttendeeEntity
import com.example.tasky.agenda.data.local.model.EventEntity

data class EventWithAttendees(
    @Embedded val event: EventEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId",
        associateBy = Junction(EventAttendeesCrossRef::class)
    )
    val attendees: List<AttendeeEntity>
)
