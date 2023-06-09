package com.example.tasky.agenda.domain.model

sealed class AgendaItem(
    open val id: String = "",
    open val title: String = "",
    open val description: String = "",
    open val time: Long = 0L,
    open val remindAt: Long = 0L,
) {
    data class Event(
        override val id: String = "",
        override val title: String = "",
        override val description: String = "",
        val from: Long = 0L,
        val to: Long = 0L,
        override val remindAt: Long = 0L,
        val host: String = "",
        val isUserEventCreator: Boolean = false,
        val attendees: List<Attendee> = listOf(),
        val photos: List<Photo> = listOf(),
        val date: String = ""
    ) : AgendaItem(
        id, title, description, from
    ) {
        override val time: Long
            get() = from
    }

    data class Reminder(
        override val id: String = "",
        override val title: String = "",
        override val description: String = "",
        override val time: Long = 0L,
        override val remindAt: Long = 0L,
        val date: String = ""
    ) : AgendaItem()

    data class Task(
        override val id: String = "",
        override val title: String = "",
        override val description: String = "",
        override val time: Long = 0L,
        override val remindAt: Long = 0L,
        val isDone: Boolean = false,
        val date: String = ""
    ) : AgendaItem()

    object Needle : AgendaItem()
}
