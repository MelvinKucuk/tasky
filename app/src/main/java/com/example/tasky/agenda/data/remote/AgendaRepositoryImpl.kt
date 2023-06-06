package com.example.tasky.agenda.data.remote

import com.example.tasky.agenda.data.remote.util.mapToAgenda
import com.example.tasky.agenda.domain.AgendaRepository
import com.example.tasky.agenda.presentation.model.Agenda
import com.example.tasky.core.data.Resource
import com.example.tasky.core.data.remote.safeApiCall
import javax.inject.Inject

class AgendaRepositoryImpl @Inject constructor(
    private val agendaService: AgendaService
) : AgendaRepository {

    override suspend fun getAgenda(): Resource<List<Agenda>> {
        return safeApiCall {
            agendaService.getAgenda(time = System.currentTimeMillis())
        }.mapToAgenda()
    }
}