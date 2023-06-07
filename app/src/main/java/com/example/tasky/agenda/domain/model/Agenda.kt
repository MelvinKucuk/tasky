package com.example.tasky.agenda.domain.model

sealed class Agenda {
    data class Event(
        val id: String = "",
        val title: String = "",
        val description: String = "",
        val from: Long = 0L,
        val to: Long = 0L,
        val remindAt: Long = 0L,
        val host: String = "",
        val isUserEventCreator: Boolean = false,
        val attendees: List<Attendee> = listOf(),
        val photos: List<Photo> = listOf(),
        val date: String = ""
    ) : Agenda()

    data class Reminder(
        val id: String = "",
        val title: String = "",
        val description: String = "",
        val time: Long = 0L,
        val remindAt: Long = 0L,
        val date: String = ""
    ) : Agenda()

    data class Task(
        val id: String = "",
        val title: String = "",
        val description: String = "",
        val time: Long = 0L,
        val remindAt: Long = 0L,
        val isDone: Boolean = false,
        val date: String = ""
    ) : Agenda()

    object Needle : Agenda()
}
