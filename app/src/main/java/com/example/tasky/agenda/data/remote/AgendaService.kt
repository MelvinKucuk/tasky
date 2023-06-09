package com.example.tasky.agenda.data.remote

import com.example.tasky.agenda.data.remote.model.AgendaResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.TimeZone

interface AgendaService {

    @GET("/agenda")
    suspend fun getAgenda(
        @Query("time") time: Long,
        @Query("timezone") timeZone: String = TimeZone.getDefault().id
    ): Response<AgendaResponse>
}