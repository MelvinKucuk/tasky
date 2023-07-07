package com.example.tasky.agenda.data

import com.example.tasky.agenda.data.local.AgendaDao
import com.example.tasky.agenda.data.mapper.toDomain
import com.example.tasky.agenda.data.remote.AgendaService
import com.example.tasky.agenda.domain.EventRepository
import com.example.tasky.agenda.domain.model.AgendaItem
import com.example.tasky.agenda.domain.model.Attendee
import com.example.tasky.core.data.Resource
import com.example.tasky.core.data.remote.safeApiCall
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val agendaDao: AgendaDao,
    private val agendaService: AgendaService
) : EventRepository {

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
}