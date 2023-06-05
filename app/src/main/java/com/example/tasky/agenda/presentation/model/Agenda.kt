package com.example.tasky.agenda.presentation.model

sealed class Agenda {
    data class Event(
        val title: String = "",
        val description: String = "",
        val date: String = "",
    ) : Agenda()

    data class Reminder(
        val title: String = "",
        val description: String = "",
        val date: String = "",
    ) : Agenda()

    data class Task(
        val title: String = "",
        val description: String = "",
        val date: String = "",
        val isDone: Boolean = false
    ) : Agenda()

    object Needle : Agenda()
}
