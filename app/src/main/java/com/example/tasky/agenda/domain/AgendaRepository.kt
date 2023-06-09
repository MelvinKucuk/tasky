package com.example.tasky.agenda.domain

import com.example.tasky.agenda.domain.model.AgendaItem
import com.example.tasky.core.data.Resource
import java.time.LocalDate

interface AgendaRepository {

    suspend fun getAgenda(date: LocalDate): Resource<List<AgendaItem>>
}