package com.example.tasky.agenda.data

import android.annotation.SuppressLint
import com.example.tasky.agenda.data.local.AgendaDao
import com.example.tasky.agenda.data.mapper.toDomain
import com.example.tasky.agenda.data.mapper.toEntity
import com.example.tasky.agenda.data.remote.AgendaService
import com.example.tasky.agenda.data.util.toCurrentTimeMilli
import com.example.tasky.agenda.data.util.toEndOfDayLong
import com.example.tasky.agenda.data.util.toStartOfDayLong
import com.example.tasky.agenda.domain.AgendaRepository
import com.example.tasky.agenda.domain.model.AgendaItem
import com.example.tasky.core.data.Resource
import com.example.tasky.core.data.remote.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import java.time.LocalDate
import javax.inject.Inject

@SuppressLint("NewApi")
class AgendaRepositoryImpl @Inject constructor(
    private val agendaService: AgendaService,
    private val agendaDao: AgendaDao
) : AgendaRepository {

    override fun getAgenda(date: LocalDate): Flow<List<AgendaItem>> = callbackFlow {
        val localEvents = getLocalEventsByDate(date)
        val localReminders = getLocalRemindersByDate(date)
        val localTasks = getLocalTasksByDate(date)

        merge(localEvents, localReminders, localTasks).collectLatest { agendaItems ->
            agendaItems.sortedBy { it.time }

            send(agendaItems)
        }
    }


    override suspend fun fetchAgenda(date: LocalDate): Boolean {
        val result = safeApiCall {
            agendaService.getAgenda(time = date.toCurrentTimeMilli())
        }

        return when (result) {
            is Resource.Success -> {
                with(result.data) {
                    events.map { agendaDao.insertEvent(it.toEntity()) }
                    reminders.map { agendaDao.insertReminder(it.toEntity()) }
                    tasks.map { agendaDao.insertTask(it.toEntity()) }
                }
                true
            }

            is Resource.Error -> false
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