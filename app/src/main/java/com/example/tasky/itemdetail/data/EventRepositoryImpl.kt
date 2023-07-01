package com.example.tasky.itemdetail.data

import com.example.tasky.agenda.data.local.AgendaDao
import com.example.tasky.agenda.data.mapper.toDomain
import com.example.tasky.core.domain.model.AgendaItem
import com.example.tasky.itemdetail.domain.EventRepository
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val agendaDao: AgendaDao
) : EventRepository {

    override suspend fun getEventById(eventId: String): AgendaItem.Event {
        return agendaDao.getEventById(eventId).toDomain()
    }
}