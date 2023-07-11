package com.example.tasky.agenda.domain.model

sealed class AgendaPhoto(val url: String) {
    class Local(url: String) : AgendaPhoto(url)
    class Remote(url: String) : AgendaPhoto(url)
}