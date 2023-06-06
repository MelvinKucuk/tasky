package com.example.tasky.agenda.data.remote.model

import com.squareup.moshi.Json

data class TaskResponse(
    @Json(name = "id")
    val id: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "time")
    val time: Long,
    @Json(name = "remindAt")
    val remindAt: Long,
    @Json(name = "isDone")
    val isDone: Boolean
)
