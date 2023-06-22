package com.example.tasky.agenda.domain

import com.example.tasky.agenda.domain.model.AgendaItem
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface AgendaRepository {

    fun getAgenda(date: LocalDate): Flow<List<AgendaItem>>

    /**
     * @return Returns true when the request is successfull and false when it isn't.
     */
    suspend fun fetchAgenda(date: LocalDate): Boolean

    suspend fun createTask(task: AgendaItem.Task, isUpdate: Boolean = false)

    suspend fun updateTaskStatus(id: String, isDone: Boolean)
}