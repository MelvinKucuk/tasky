@file:SuppressLint("NewApi")

package com.example.tasky.agenda.presentation.viewmodel

import android.annotation.SuppressLint
import com.example.tasky.agenda.domain.model.AgendaItem
import com.example.tasky.agenda.domain.model.Day
import com.example.tasky.agenda.presentation.MenuItem
import com.example.tasky.agenda.presentation.util.agendaItemMenu
import com.example.tasky.agenda.presentation.util.fabMenu
import com.example.tasky.agenda.presentation.util.profileMenu

data class AgendaState(
    val selectedMonth: String = "",
    val userInitials: String = "",
    val days: List<Day> = listOf(),
    val agendaItems: List<AgendaItem> = listOf(),
    val agendaItemMenu: List<MenuItem> = agendaItemMenu(),
    val profileMenu: List<MenuItem> = profileMenu(),
    val fabMenu: List<MenuItem> = fabMenu(),
    val showFab: Boolean = false,
    val showProfileMenu: Boolean = false,
    val showCalendar: Boolean = false,
    val errorMessage: String? = null
)