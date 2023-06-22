package com.example.tasky.agenda.data.mapper

import com.example.tasky.agenda.data.local.model.TaskEntity
import com.example.tasky.agenda.data.remote.model.TaskResponse
import com.example.tasky.agenda.domain.model.AgendaItem

fun TaskResponse.toDomain() =
    AgendaItem.Task(
        id = id,
        title = title,
        description = description,
        time = time,
        remindAt = remindAt,
        isDone = isDone,
    )

fun TaskResponse.toEntity() =
    TaskEntity(
        id = id,
        title = title,
        description = description,
        time = time,
        remindAt = remindAt,
        isDone = isDone
    )

fun TaskEntity.toDomain() =
    AgendaItem.Task(
        id = id,
        title = title,
        description = description,
        time = time,
        remindAt = remindAt,
        isDone = isDone,
    )

fun AgendaItem.Task.toEntity() =
    TaskEntity(
        id = id,
        title = title,
        description = description,
        time = time,
        remindAt = remindAt,
        isDone = isDone
    )

fun AgendaItem.Task.toRemote() =
    TaskResponse(
        id = id,
        title = title,
        description = description,
        time = time,
        remindAt = remindAt,
        isDone = isDone
    )