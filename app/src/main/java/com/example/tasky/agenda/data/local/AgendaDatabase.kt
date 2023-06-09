package com.example.tasky.agenda.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tasky.agenda.data.local.model.AttendeeEntity
import com.example.tasky.agenda.data.local.model.EventEntity
import com.example.tasky.agenda.data.local.model.ReminderEntity
import com.example.tasky.agenda.data.local.model.TaskEntity

@Database(
    entities = [
        EventEntity::class,
        ReminderEntity::class,
        TaskEntity::class,
        AttendeeEntity::class
    ],
    version = 1
)
abstract class AgendaDatabase : RoomDatabase() {

    abstract val dao: AgendaDao
}