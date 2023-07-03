package com.example.tasky.agenda.data.mapper

import com.example.tasky.agenda.data.remote.model.AgendaResponse
import com.example.tasky.agenda.domain.model.AgendaItem
import com.example.tasky.core.data.Resource

fun Resource<AgendaResponse>.toAgenda(): Resource<List<AgendaItem>> {
    return when (this) {
        is Resource.Error -> Resource.Error(this.errorMessage)
        is Resource.Success -> {
            var agendaList: List<AgendaItem> = mutableListOf()

            agendaList = this.data.events.map {
                it.toDomain()
            } + this.data.reminders.map {
                it.toDomain()
            } + this.data.tasks.map {
                it.toDomain()
            }

            Resource.Success(agendaList)
        }
    }
}