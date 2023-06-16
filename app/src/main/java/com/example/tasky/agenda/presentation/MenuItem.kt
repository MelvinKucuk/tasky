package com.example.tasky.agenda.presentation

import com.example.tasky.R

enum class MenuItem(val text: Int) {
    EVENT(text = R.string.event),
    TASK(text = R.string.task),
    REMINDER(text = R.string.reminder),
    LOGOUT(text = R.string.logout),
    OPEN(text = R.string.open),
    EDIT(text = R.string.edit),
    DELETE(text = R.string.delete)
}
