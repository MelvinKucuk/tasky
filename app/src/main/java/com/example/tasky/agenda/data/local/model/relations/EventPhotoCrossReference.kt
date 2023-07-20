package com.example.tasky.agenda.data.local.model.relations

import androidx.room.Entity

@Entity(primaryKeys = ["id", "key"])
data class EventPhotoCrossReference(
    val id: String,
    val key: String
)
