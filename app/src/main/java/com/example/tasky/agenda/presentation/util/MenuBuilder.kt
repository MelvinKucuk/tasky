package com.example.tasky.agenda.presentation.util

import com.example.tasky.agenda.presentation.MenuItem

fun fabMenu() =
    listOf(
        MenuItem.Event(),
        MenuItem.Reminder(),
        MenuItem.Task(),
    )

fun agendaItemMenu() =
    listOf(
        MenuItem.Open(),
        MenuItem.Edit(),
        MenuItem.Delete(),
    )

fun profileMenu() =
    listOf(
        MenuItem.Logout()
    )