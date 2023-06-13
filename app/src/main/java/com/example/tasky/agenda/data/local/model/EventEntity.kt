package com.example.tasky.agenda.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EventEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val from: Long,
    val to: Long,
    val remindAt: Long,
    val host: String,
    val isUserEventCreator: Boolean,
)
