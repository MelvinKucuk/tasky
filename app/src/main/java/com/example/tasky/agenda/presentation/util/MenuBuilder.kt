package com.example.tasky.agenda.presentation.util

import com.example.tasky.agenda.presentation.MenuItem

fun fabMenu() =
    listOf(
        MenuItem.EVENT,
        MenuItem.REMINDER,
        MenuItem.TASK,
    )

fun agendaItemMenu() =
    listOf(
        MenuItem.OPEN,
        MenuItem.EDIT,
        MenuItem.DELETE,
    )

fun profileMenu() =
    listOf(
        MenuItem.LOGOUT
    )