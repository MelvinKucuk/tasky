package com.example.tasky.agenda.presentation.home

sealed class AgendaItemEvent {
    data class MenuClick(val menu: MenuItem) : AgendaItemEvent()
    data class DoneClick(val isDone: Boolean) : AgendaItemEvent()
}
