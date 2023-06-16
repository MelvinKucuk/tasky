package com.example.tasky.agenda.presentation

sealed class AgendaItemEvent {
    data class MenuClick(val menu: MenuItem) : AgendaItemEvent()
    data class DoneClick(val isDone: Boolean) : AgendaItemEvent()
    object MenuDismiss : AgendaItemEvent()
    object MoreOptions : AgendaItemEvent()
}
