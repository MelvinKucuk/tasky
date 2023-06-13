package com.example.tasky.agenda.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AttendeeEntity(
    @PrimaryKey
    val userId: String,
    val email: String,
    val fullName: String,
    val eventId: String?,
    val isGoing: Boolean?,
    val remindAt: Long?
)
