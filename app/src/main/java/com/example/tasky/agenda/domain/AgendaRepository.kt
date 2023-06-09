package com.example.tasky.agenda.domain

import com.example.tasky.agenda.domain.model.AgendaItem
import com.example.tasky.core.data.Resource

interface AgendaRepository {

    suspend fun getAgenda(): Resource<List<AgendaItem>>
}