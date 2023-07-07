package com.example.tasky.agenda.domain

import com.example.tasky.agenda.domain.model.AgendaItem
import com.example.tasky.agenda.domain.model.Attendee

interface EventRepository {
    suspend fun getEventById(eventId: String): AgendaItem.Event
    suspend fun getValidUser(email: String): Attendee?
}