package com.example.tasky.agenda.data

import com.example.tasky.agenda.data.local.AgendaDao
import com.example.tasky.agenda.data.mapper.toDomain
import com.example.tasky.agenda.domain.EventRepository
import com.example.tasky.agenda.domain.model.AgendaItem
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val agendaDao: AgendaDao
) : EventRepository {

    override suspend fun getEventById(eventId: String): AgendaItem.Event {
        return agendaDao.getEventById(eventId).toDomain()
    }
}