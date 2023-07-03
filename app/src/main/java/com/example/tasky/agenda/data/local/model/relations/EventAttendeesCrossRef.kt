package com.example.tasky.agenda.data.local.model.relations

import androidx.room.Entity

@Entity(primaryKeys = ["id", "userId"])
data class EventAttendeesCrossRef(
    val id: String,
    val userId: String
)
