package com.example.tasky.agenda.presentation.model

sealed class Agenda {
    data class AgendaUI(
        val title: String = "",
        val description: String = "",
        val date: String = "",
        val isDone: Boolean = false
    ) : Agenda()

    object Needle : Agenda()
}
