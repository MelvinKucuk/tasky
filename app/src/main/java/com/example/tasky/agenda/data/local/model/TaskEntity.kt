package com.example.tasky.agenda.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val time: Long,
    val remindAt: Long,
    val isDone: Boolean
)
