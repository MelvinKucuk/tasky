package com.example.tasky.agenda.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.tasky.agenda.data.local.model.AttendeeEntity
import com.example.tasky.agenda.data.local.model.EventEntity
import com.example.tasky.agenda.data.local.model.PhotoEntity
import com.example.tasky.agenda.data.local.model.ReminderEntity
import com.example.tasky.agenda.data.local.model.TaskEntity
import com.example.tasky.agenda.data.local.model.relations.EventAttendeesCrossRef
import com.example.tasky.agenda.data.local.model.relations.EventPhotoCrossReference
import com.example.tasky.agenda.data.local.model.relations.EventWithAttendeesWithPhotos
import kotlinx.coroutines.flow.Flow

@Dao
interface AgendaDao {

    @Upsert
    suspend fun insertEvent(event: EventEntity)

    @Query("DELETE FROM EventEntity WHERE id = :id")
    suspend fun deleteEvent(id: String)

    @Query("SELECT * FROM EventEntity WHERE id = :id")
    suspend fun getEventById(id: String): EventWithAttendeesWithPhotos

    @Query(
        """
            SELECT * FROM EventEntity
            WHERE `from` >= :startOfDay
            AND `from` < :endOfDay
        """
    )
    @Transaction
    fun getEventsForGivenDay(
        startOfDay: Long,
        endOfDay: Long
    ): Flow<List<EventWithAttendeesWithPhotos>>

    @Upsert
    suspend fun insertReminder(reminder: ReminderEntity)

    @Query("DELETE FROM ReminderEntity WHERE id = :id")
    suspend fun deleteReminder(id: String)

    @Query("SELECT * FROM ReminderEntity WHERE id = :id")
    suspend fun getReminderById(id: String): ReminderEntity

    @Query(
        """
            SELECT * FROM ReminderEntity
            WHERE time >= :startOfDay
            AND time < :endOfDay
        """
    )
    fun getRemindersForGivenDay(startOfDay: Long, endOfDay: Long): Flow<List<ReminderEntity>>

    @Upsert
    suspend fun insertTask(task: TaskEntity)

    @Query("DELETE FROM TaskEntity WHERE id = :id")
    suspend fun deleteTaskById(id: String)

    @Query("SELECT * FROM TaskEntity WHERE id = :id")
    suspend fun getTaskById(id: String): TaskEntity

    @Query(
        """
            SELECT * FROM TaskEntity
            WHERE time >= :startOfDay
            AND time < :endOfDay
        """
    )
    fun getTasksForGivenDay(startOfDay: Long, endOfDay: Long): Flow<List<TaskEntity>>

    @Upsert
    suspend fun insertAttendee(attendee: AttendeeEntity)

    @Upsert
    suspend fun insertEventAttendeeCrossRef(ref: EventAttendeesCrossRef)

    @Upsert
    suspend fun insertPhoto(photo: PhotoEntity)

    @Upsert
    suspend fun insertEventPhotoCrossRef(ref: EventPhotoCrossReference)
}