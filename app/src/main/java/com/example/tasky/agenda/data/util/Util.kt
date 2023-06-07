package com.example.tasky.agenda.data.util

import com.example.tasky.agenda.data.mapper.toDomain
import com.example.tasky.agenda.data.remote.model.AgendaResponse
import com.example.tasky.agenda.domain.model.Agenda
import com.example.tasky.core.data.Resource

fun Resource<AgendaResponse>.mapToAgenda(): Resource<List<Agenda>> {
    when (this) {
        is Resource.Error -> return Resource.Error(this.errorMessage)
        is Resource.Success -> {
            var agendaList: List<Agenda> = mutableListOf()

            agendaList = agendaList + this.data.events.map {
                it.toDomain()
            }

            agendaList = agendaList + this.data.reminders.map {
                it.toDomain()
            }

            agendaList = agendaList + this.data.tasks.map {
                it.toDomain()
            }
            return Resource.Success(agendaList)
        }
    }
}