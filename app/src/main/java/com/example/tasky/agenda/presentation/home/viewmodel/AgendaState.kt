@file:SuppressLint("NewApi")

package com.example.tasky.agenda.presentation.home.viewmodel

import android.annotation.SuppressLint
import com.example.tasky.agenda.domain.model.AgendaItem
import com.example.tasky.agenda.domain.model.Day
import com.example.tasky.agenda.presentation.home.MenuItem
import com.example.tasky.agenda.presentation.home.util.agendaItemMenu
import com.example.tasky.agenda.presentation.home.util.fabMenu
import com.example.tasky.agenda.presentation.home.util.profileMenu

data class AgendaState(
    val selectedMonth: String = "",
    val userInitials: String = "",
    val days: List<Day> = listOf(),
    val agendaItems: List<AgendaItem> = listOf(),
    val agendaItemMenu: List<MenuItem> = agendaItemMenu(),
    val profileMenu: List<MenuItem> = profileMenu(),
    val fabMenu: List<MenuItem> = fabMenu(),
    val isLoggedOut: Boolean = false,
    val showFab: Boolean = false,
    val showProfileMenu: Boolean = false,
    val showCalendar: Boolean = false,
    val errorMessage: String? = null,
    val navigateToEventDetail: String? = "12898688"
)