package com.example.tasky.agenda.domain

import com.example.tasky.agenda.domain.model.AgendaItem


interface EventUploader {
    suspend fun uploadEvent(event: AgendaItem.Event)
}