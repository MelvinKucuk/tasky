package com.example.tasky.agenda.data

import android.annotation.SuppressLint
import com.example.tasky.agenda.data.local.AgendaDao
import com.example.tasky.agenda.data.mapper.toDomain
import com.example.tasky.agenda.data.mapper.toEntity
import com.example.tasky.agenda.data.remote.AgendaService
import com.example.tasky.agenda.data.util.asTodayEndLong
import com.example.tasky.agenda.data.util.asTodayStartLong
import com.example.tasky.agenda.data.util.toCurrentTimeMilli
import com.example.tasky.agenda.domain.AddNeedleToAgenda
import com.example.tasky.agenda.domain.AgendaRepository
import com.example.tasky.agenda.domain.model.AgendaItem
import com.example.tasky.core.data.Resource
import com.example.tasky.core.data.remote.safeApiCall
import java.time.LocalDate
import javax.inject.Inject

@SuppressLint("NewApi")
class AgendaRepositoryImpl @Inject constructor(
    private val agendaService: AgendaService,
    private val agendaDao: AgendaDao
) : AgendaRepository {

    override suspend fun getAgenda(date: LocalDate): Resource<List<AgendaItem>> {
        val result = safeApiCall {
            agendaService.getAgenda(time = date.toCurrentTimeMilli())
        }

        when (result) {
            is Resource.Success -> {
                with(result.data) {
                    events.map { agendaDao.insertEvent(it.toEntity()) }
                    reminders.map { agendaDao.insertReminder(it.toEntity()) }
                    tasks.map { agendaDao.insertTask(it.toEntity()) }
                }
                val localEvents = getLocalEventsByDate(date)
                val localReminders = getLocalRemindersByDate(date)
                val localTasks = getLocalTasksByDate(date)

                val agendaItems: MutableList<AgendaItem> =
                    (localEvents + localReminders + localTasks).toMutableList()

                agendaItems.sortBy { it.time }

                AddNeedleToAgenda(date.toEpochDay(), agendaItems)

                return Resource.Success(agendaItems)
            }

            is Resource.Error -> return Resource.Error(result.errorMessage)
        }
    }


    private suspend fun getLocalEventsByDate(date: LocalDate): List<AgendaItem.Event> {
        val day = date.asTodayStartLong()
        val endOfDay = date.asTodayEndLong()
        return agendaDao.getEventsForGivenDay(day, endOfDay).map { it.toDomain() }
    }

    private suspend fun getLocalRemindersByDate(date: LocalDate): List<AgendaItem.Reminder> {
        val day = date.asTodayStartLong()
        val endOfDay = date.asTodayEndLong()
        return agendaDao.getRemindersForGivenDay(day, endOfDay).map { it.toDomain() }
    }

    private suspend fun getLocalTasksByDate(date: LocalDate): List<AgendaItem.Task> {
        val day = date.asTodayStartLong()
        val endOfDay = date.asTodayEndLong()
        return agendaDao.getTasksForGivenDay(day, endOfDay).map { it.toDomain() }
    }
}