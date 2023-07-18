package com.example.tasky.agenda.data.remote

import com.example.tasky.agenda.data.remote.model.AgendaResponse
import com.example.tasky.agenda.data.remote.model.AttendeeWithExist
import com.example.tasky.agenda.data.remote.model.TaskResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query
import java.util.TimeZone

interface AgendaService {

    @GET("/agenda")
    suspend fun getAgenda(
        @Query("time") time: Long,
        @Query("timezone") timeZone: String = TimeZone.getDefault().id
    ): Response<AgendaResponse>

    @POST("/task")
    suspend fun createTask(@Body task: TaskResponse): Response<TaskResponse>

    @PUT("/task")
    suspend fun updateTask(@Body task: TaskResponse): Response<ResponseBody>

    @GET("/attendee")
    suspend fun getValidUser(@Query("email") email: String): Response<AttendeeWithExist>

    @Multipart
    @POST("/event")
    suspend fun createEvent(@Part body: MultipartBody.Part, @Part files: List<MultipartBody.Part>)

    @Multipart
    @PUT("/event")
    suspend fun updateEvent(@Part body: MultipartBody.Part, @Part files: List<MultipartBody.Part>)
}