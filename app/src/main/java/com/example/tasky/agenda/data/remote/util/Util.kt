package com.example.tasky.agenda.data.remote.util

import android.annotation.SuppressLint
import com.example.tasky.agenda.data.remote.model.AgendaResponse
import com.example.tasky.agenda.presentation.model.Agenda
import com.example.tasky.core.data.Resource
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

fun Resource<AgendaResponse>.mapToAgenda(): Resource<List<Agenda>> {
    if (this is Resource.Error) {
        return Resource.Error(this.errorMessage)
    } else {
        var agendaList: List<Agenda> = mutableListOf()

        with(this as Resource.Success) {
            agendaList = agendaList + this.data.events.map {
                Agenda.Event(
                    title = it.title,
                    description = it.description,
                    date = it.from.toCurrentTime(),
                )
            }

            agendaList = agendaList + this.data.reminders.map {
                Agenda.Reminder(
                    title = it.title,
                    description = it.description,
                    date = it.remindAt.toCurrentTime(),
                )
            }

            agendaList = agendaList + this.data.tasks.map {
                Agenda.Task(
                    title = it.title,
                    description = it.description,
                    date = it.remindAt.toCurrentTime(),
                    isDone = it.isDone
                )
            }
        }
        return Resource.Success(agendaList)
    }
}

@SuppressLint("NewApi")
fun Long.toCurrentTime(): String {
    return LocalDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        ZoneId.systemDefault()
    ).toLocalTime().toString()
}