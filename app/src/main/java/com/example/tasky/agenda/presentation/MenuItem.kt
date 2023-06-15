package com.example.tasky.agenda.presentation

import com.example.tasky.R

sealed class MenuItem(val text: Int) {
    class Event(text: Int = R.string.event) : MenuItem(text)
    class Task(text: Int = R.string.task) : MenuItem(text)
    class Reminder(text: Int = R.string.reminder) : MenuItem(text)
    class Logout(text: Int = R.string.logout) : MenuItem(text)
    class Open(text: Int = R.string.open) : MenuItem(text)
    class Edit(text: Int = R.string.edit) : MenuItem(text)
    class Delete(text: Int = R.string.delete) : MenuItem(text)
}
