package com.example.tasky.agenda.presentation.viewmodel

import com.example.tasky.agenda.domain.model.AgendaItem
import com.example.tasky.agenda.presentation.AgendaItemEvent
import com.example.tasky.agenda.presentation.MenuItem
import java.time.LocalDate

sealed class AgendaEvent {
    object Logout : AgendaEvent()
    object ProfileClick : AgendaEvent()
    object ProfileMenuDismiss : AgendaEvent()
    object FabDismiss : AgendaEvent()
    object FabClicked : AgendaEvent()
    object MonthClick : AgendaEvent()
    object MonthDismiss : AgendaEvent()
    data class OnAgendaItemEvent(
        val event: AgendaItemEvent,
        val agendaItem: AgendaItem
    ) : AgendaEvent()

    data class DayClicked(val numberOfDay: Int) : AgendaEvent()
    data class NewItem(val item: MenuItem) : AgendaEvent()
    data class DateSelected(val date: LocalDate) : AgendaEvent()
}
