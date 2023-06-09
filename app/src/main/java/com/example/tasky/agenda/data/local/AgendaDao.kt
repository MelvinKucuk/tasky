package com.example.tasky.agenda.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.tasky.agenda.data.local.model.EventEntity
import com.example.tasky.agenda.data.local.model.ReminderEntity
import com.example.tasky.agenda.data.local.model.TaskEntity

@Dao
interface AgendaDao {

    @Upsert
    suspend fun insertEvent(event: EventEntity)

    @Query("DELETE FROM EventEntity WHERE id = :id")
    suspend fun deleteEvent(id: String)

    @Query("SELECT * FROM EventEntity WHERE id = :id")
    suspend fun getEventById(id: String): EventEntity

    @Query(
        """
            SELECT * FROM EventEntity
            WHERE `from` >= :day
            AND `from` < :endOfDay
        """
    )
    suspend fun getEventsForGivenDay(day: Long, endOfDay: Long): List<EventEntity>

    @Upsert
    suspend fun insertReminder(reminder: ReminderEntity)

    @Query("DELETE FROM ReminderEntity WHERE id = :id")
    suspend fun deleteReminder(id: String)

    @Query("SELECT * FROM ReminderEntity WHERE id = :id")
    suspend fun getReminderById(id: String): ReminderEntity

    @Query(
        """
            SELECT * FROM ReminderEntity
            WHERE time >= :day
            AND time < :endOfDay
        """
    )
    suspend fun getRemindersForGivenDay(day: Long, endOfDay: Long): List<ReminderEntity>

    @Upsert
    suspend fun insertTask(task: TaskEntity)

    @Query("DELETE FROM TaskEntity WHERE id = :id")
    suspend fun deleteTaskById(id: String)

    @Query("SELECT * FROM TaskEntity WHERE id = :id")
    suspend fun getTaskById(id: String): TaskEntity

    @Query(
        """
            SELECT * FROM TaskEntity
            WHERE time >= :day
            AND time < :endOfDay
        """
    )
    suspend fun getTasksForGivenDay(day: Long, endOfDay: Long): List<TaskEntity>
}