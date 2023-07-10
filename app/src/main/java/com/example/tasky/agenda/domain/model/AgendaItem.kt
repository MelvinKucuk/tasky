package com.example.tasky.agenda.domain.model

import com.example.tasky.agenda.domain.util.toCurrentTime

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
        override val remindAt: Long = 0L,
        val from: Long = 0L,
        val to: Long = 0L,
        val host: String = "",
        val isUserEventCreator: Boolean = false,
        val attendees: List<Attendee> = listOf(),
        val photos: List<AgendaPhoto> = listOf()
    ) : AgendaItem(
        id, title, description, from
    ) {
        override val time: Long
            get() = from

        val date: String
            get() = "${from.toCurrentTime()} - ${to.toCurrentTime()}"
    }

    data class Reminder(
        override val id: String = "",
        override val title: String = "",
        override val description: String = "",
        override val time: Long = 0L,
        override val remindAt: Long = 0L,
    ) : AgendaItem() {
        val date: String
            get() = time.toCurrentTime()
    }

    data class Task(
        override val id: String = "",
        override val title: String = "",
        override val description: String = "",
        override val time: Long = 0L,
        override val remindAt: Long = 0L,
        val isDone: Boolean = false,
    ) : AgendaItem() {
        val date: String
            get() = time.toCurrentTime()
    }

    object Needle : AgendaItem()
}
