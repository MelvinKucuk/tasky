package com.example.tasky.agenda.domain

import com.example.tasky.core.domain.model.AgendaItem

interface EventRepository {
    suspend fun getEventById(eventId: String): AgendaItem.Event
}