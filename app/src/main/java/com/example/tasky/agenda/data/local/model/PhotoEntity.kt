package com.example.tasky.agenda.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PhotoEntity(
    @PrimaryKey
    val key: String,
    val eventId: String,
    val url: String
)
