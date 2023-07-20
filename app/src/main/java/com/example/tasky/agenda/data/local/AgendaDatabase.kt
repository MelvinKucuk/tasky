package com.example.tasky.agenda.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tasky.agenda.data.local.model.AttendeeEntity
import com.example.tasky.agenda.data.local.model.EventEntity
import com.example.tasky.agenda.data.local.model.PhotoEntity
import com.example.tasky.agenda.data.local.model.ReminderEntity
import com.example.tasky.agenda.data.local.model.TaskEntity
import com.example.tasky.agenda.data.local.model.relations.EventAttendeesCrossRef

@Database(
    entities = [
        EventEntity::class,
        ReminderEntity::class,
        TaskEntity::class,
        AttendeeEntity::class,
        EventAttendeesCrossRef::class,
        PhotoEntity::class
    ],
    version = 1
)
abstract class AgendaDatabase : RoomDatabase() {

    abstract val dao: AgendaDao
}