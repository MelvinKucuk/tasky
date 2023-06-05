package com.example.tasky.agenda.presentation.model

sealed class Agenda {
    class Event(
        val title: String = "",
        val description: String = "",
        val date: String = "",
    ) : Agenda()

    class Reminder(
        val title: String = "",
        val description: String = "",
        val date: String = "",
    ) : Agenda()

    class Task(
        val title: String = "",
        val description: String = "",
        val date: String = "",
        val isDone: Boolean = false
    ) : Agenda()

    object Needle : Agenda()
}
