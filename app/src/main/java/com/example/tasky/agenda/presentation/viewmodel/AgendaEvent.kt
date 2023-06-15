package com.example.tasky.agenda.presentation.viewmodel

import com.example.tasky.agenda.presentation.AgendaItemEvent
import com.example.tasky.agenda.presentation.MenuItem

sealed class AgendaEvent {
    object Logout : AgendaEvent()
    object ProfileClick : AgendaEvent()
    object ProfileMenuDismiss : AgendaEvent()
    object FabDismiss : AgendaEvent()
    object FabClicked : AgendaEvent()
    object MonthClick : AgendaEvent()
    data class OnAgendaItemEvent(val event: AgendaItemEvent) : AgendaEvent()
    data class DayClicked(val numberOfDay: Int) : AgendaEvent()
    data class NewItem(val item: MenuItem) : AgendaEvent()

}
