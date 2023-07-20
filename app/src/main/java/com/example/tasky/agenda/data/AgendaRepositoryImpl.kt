package com.example.tasky.agenda.data

import android.annotation.SuppressLint
import com.example.tasky.agenda.data.local.AgendaDao
import com.example.tasky.agenda.data.local.model.relations.EventAttendeesCrossRef
import com.example.tasky.agenda.data.local.model.relations.EventPhotoCrossReference
import com.example.tasky.agenda.data.mapper.toDomain
import com.example.tasky.agenda.data.mapper.toEntity
import com.example.tasky.agenda.data.mapper.toRemote
import com.example.tasky.agenda.data.remote.AgendaService
import com.example.tasky.agenda.data.remote.model.TaskResponse
import com.example.tasky.agenda.domain.AgendaRepository
import com.example.tasky.agenda.domain.EventUploader
import com.example.tasky.agenda.domain.model.AgendaItem
import com.example.tasky.agenda.domain.model.AgendaPhoto
import com.example.tasky.agenda.domain.model.Attendee
import com.example.tasky.agenda.domain.util.toCurrentTimeMilli
import com.example.tasky.agenda.domain.util.toEndOfDayLong
import com.example.tasky.agenda.domain.util.toStartOfDayLong
import com.example.tasky.core.data.Resource
import com.example.tasky.core.data.remote.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

@SuppressLint("NewApi")
class AgendaRepositoryImpl @Inject constructor(
    private val agendaService: AgendaService,
    private val agendaDao: AgendaDao,
    private val eventUploader: EventUploader,
) : AgendaRepository {

    override fun getAgenda(date: LocalDate): Flow<List<AgendaItem>> {
        val localEvents = getLocalEventsByDate(date)
        val localReminders = getLocalRemindersByDate(date)
        val localTasks = getLocalTasksByDate(date)

        return combine(localEvents, localReminders, localTasks) { events, reminders, tasks ->
            (events + reminders + tasks).sortedBy { it.time }
        }
    }


    override suspend fun fetchAgenda(date: LocalDate): Boolean {
        val result = safeApiCall {
            agendaService.getAgenda(time = date.toCurrentTimeMilli())
        }

        return when (result) {
            is Resource.Success -> {
                with(result.data) {
                    events.map { insertEvent(it.toDomain()) }
                    reminders.map { agendaDao.insertReminder(it.toEntity()) }
                    tasks.map { agendaDao.insertTask(it.toEntity()) }
                }
                true
            }

            is Resource.Error -> false
        }
    }

    override suspend fun createTask(task: AgendaItem.Task, isUpdate: Boolean) {
        agendaDao.insertTask(task = task.toEntity())
        createTaskRemotely(task = task.toRemote(), isUpdate = isUpdate)
    }

    private suspend fun createTaskRemotely(task: TaskResponse, isUpdate: Boolean) {
        if (isUpdate) {
            agendaService.updateTask(task)
        } else {
            agendaService.createTask(task)
        }
    }

    override suspend fun updateTaskStatus(id: String, isDone: Boolean) {
        val task = agendaDao.getTaskById(id).copy(isDone = isDone)
        createTask(task = task.toDomain(), isUpdate = true)
    }

    override suspend fun insertEvent(event: AgendaItem.Event) {
        supervisorScope {
            event.attendees.map {
                launch {
                    agendaDao.insertAttendee(it.toEntity())
                    agendaDao.insertEventAttendeeCrossRef(
                        EventAttendeesCrossRef(
                            id = event.id,
                            userId = it.userId
                        )
                    )
                }
            }.map { it.join() }
        }

        supervisorScope {
            event.photos.filterIsInstance<AgendaPhoto.Remote>().map {
                launch {
                    agendaDao.insertPhoto(it.toEntity())
                    agendaDao.insertEventPhotoCrossRef(
                        EventPhotoCrossReference(
                            id = event.id,
                            key = it.key
                        )
                    )
                }
            }.map { it.join() }
        }

        agendaDao.insertEvent(event.toEntity())
    }

    override suspend fun createEvent(event: AgendaItem.Event) {
        val id = UUID.randomUUID().toString()
        insertEvent(
            event.copy(
                id = id
            )
        )
        eventUploader.uploadEvent(
            event.copy(
                id = id
            )
        )
    }

    override suspend fun getEventById(eventId: String): AgendaItem.Event {
        return agendaDao.getEventById(eventId).toDomain()
    }

    override suspend fun getValidUser(email: String): Attendee? {
        return when (val result = safeApiCall { agendaService.getValidUser(email) }) {
            is Resource.Success -> {
                if (result.data.doesUserExist) {
                    result.data.attendee?.toDomain()
                } else {
                    null
                }
            }

            is Resource.Error -> null
        }
    }

    private fun getLocalEventsByDate(date: LocalDate): Flow<List<AgendaItem.Event>> {
        val day = date.toStartOfDayLong()
        val endOfDay = date.toEndOfDayLong()
        return agendaDao.getEventsForGivenDay(day, endOfDay)
            .map { list -> list.map { it.toDomain() } }
    }

    private fun getLocalRemindersByDate(date: LocalDate): Flow<List<AgendaItem.Reminder>> {
        val day = date.toStartOfDayLong()
        val endOfDay = date.toEndOfDayLong()
        return agendaDao.getRemindersForGivenDay(day, endOfDay)
            .map { list -> list.map { it.toDomain() } }
    }

    private fun getLocalTasksByDate(date: LocalDate): Flow<List<AgendaItem.Task>> {
        val day = date.toStartOfDayLong()
        val endOfDay = date.toEndOfDayLong()
        return agendaDao.getTasksForGivenDay(day, endOfDay)
            .map { list -> list.map { it.toDomain() } }
    }
}